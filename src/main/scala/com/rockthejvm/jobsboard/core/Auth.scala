package com.rockthejvm.jobsboard.core

import cats.effect.*
import cats.implicits.*
import com.rockthejvm.jobsboard.domain.security.*
import com.rockthejvm.jobsboard.config.*
import com.rockthejvm.jobsboard.domain.auth.*
import com.rockthejvm.jobsboard.domain.user.*

import org.typelevel.log4cats.Logger
import tsec.authentication.AugmentedJWT
import tsec.mac.jca.HMACSHA256
import tsec.authentication.JWTAuthenticator
import tsec.passwordhashers.jca.BCrypt
import tsec.passwordhashers.PasswordHash

import tsec.authentication.IdentityStore
import tsec.authentication.BackingStore
import tsec.common.SecureRandomId
import cats.data.OptionT
import scala.concurrent.duration._

trait Auth[F[_]] {
  def login(email: String, password: String): F[Option[JwtToken]]
  def signUp(newUserInfo: NewUserInfo): F[Option[User]]

  def changePassword(
      email: String,
      newPasswordInfo: NewPasswordInfo
  ): F[Either[String, Option[User]]]

  def delete(email: String): F[Boolean]

  def authenticator: Authenticator[F]
}

class LiveAuth[F[_]: Async: Logger] private (
    users: Users[F],
    override val authenticator: Authenticator[F]
) extends Auth[F] {
  def login(email: String, password: String): F[Option[JwtToken]] = for {
    maybeUser <- users.find(email)
    maybeValidatedUser <- maybeUser.filterA(user =>
      BCrypt.checkpwBool[F](password, PasswordHash[BCrypt](user.hashedPassword))
    )
    maybeJwtToken <- maybeValidatedUser.traverse(user => authenticator.create(user.email))
  } yield maybeJwtToken

  def signUp(newUserInfo: NewUserInfo): F[Option[User]] =
    users.find(newUserInfo.email).flatMap {
      case Some(_) => None.pure[F]
      case None =>
        for {
          hashedPassword <- BCrypt.hashpw[F](newUserInfo.password)
          user <- User(
            newUserInfo.email,
            hashedPassword,
            newUserInfo.firstName,
            newUserInfo.lastName,
            newUserInfo.company,
            Role.RECRUITER
          ).pure[F]
          _ <- users.create(user)
        } yield Some(user)
    }

  def changePassword(
      email: String,
      newPasswordInfo: NewPasswordInfo
  ): F[Either[String, Option[User]]] = {
    def updateUser(user: User, newPassword: String): F[Option[User]] =
      for {
        hashedPassword <- BCrypt.hashpw[F](newPasswordInfo.newPassword)
        updatedUser    <- users.update(user.copy(hashedPassword = hashedPassword))
      } yield updatedUser

    def checkAndUpdate(
        user: User,
        oldPassword: String,
        newPassword: String
    ): F[Either[String, Option[User]]] =
      for {
        passCheck <- BCrypt.checkpwBool[F](
          newPasswordInfo.oldPassword,
          PasswordHash[BCrypt](user.hashedPassword)
        )
        updateResult <-
          if (passCheck) updateUser(user, newPassword).map(Right(_))
          else Left("Invalid password").pure[F]
      } yield updateResult

    users.find(email).flatMap {
      case None => Right(None).pure[F]
      case Some(user) =>
        val NewPasswordInfo(oldPassword, newPassword) = newPasswordInfo
        checkAndUpdate(user, newPasswordInfo.oldPassword, newPasswordInfo.newPassword)
    }

  }

  def delete(email: String): F[Boolean] = users.delete(email)

}

object LiveAuth {
  def apply[F[_]: Async: Logger](users: Users[F])(securityConfig: SecurityConfig): F[LiveAuth[F]] = {

    // 1. Identity store => OptionT[F, User]

    val idStore: IdentityStore[F, String, User] = (email: String) => OptionT(users.find(email))

    // 2. backing store for JWT tokens
    val tokenStoreF = Ref.of[F, Map[SecureRandomId, JwtToken]](Map.empty).map { ref =>
      new BackingStore[F, SecureRandomId, JwtToken] {
        // mutable map - race conditions
        // ref
        override def get(id: SecureRandomId): OptionT[F, JwtToken] =
          OptionT(ref.get.map(_.get(id)))

        override def put(elem: JwtToken): F[JwtToken] =
          ref.modify(store => (store + (elem.id -> elem), elem))

        override def delete(id: SecureRandomId): F[Unit] =
          ref.modify(store => (store - id, ()))

        override def update(v: JwtToken): F[JwtToken] =
          put(v)
      }
    }

    // 3. hashing key
    val keyF = HMACSHA256.buildKey[F](securityConfig.secret.getBytes("UTF-8")) // TODO move to config

    // 4. authenticator
      for {
        key        <- keyF
        tokenStore <- tokenStoreF
        authenticator = JWTAuthenticator.backed.inBearerToken(
          expiryDuration = securityConfig.jwtExpiryDuration,  // expiration of tokens
          maxIdle = None,          // max idle time (optional)
          identityStore = idStore, // identity store
          tokenStore = tokenStore,
          signingKey = key // hash key
        )
      } yield // 5. live auth
        new LiveAuth[F](users, authenticator)

  }
}
