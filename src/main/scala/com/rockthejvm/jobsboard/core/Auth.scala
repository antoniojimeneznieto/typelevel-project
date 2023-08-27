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
  def login(email: String, password: String): F[Option[User]]
  def signUp(newUserInfo: NewUserInfo): F[Option[User]]

  def changePassword(
      email: String,
      newPasswordInfo: NewPasswordInfo
  ): F[Either[String, Option[User]]]

  def delete(email: String): F[Boolean]

  def sendPasswordRecoveryToken(email: String): F[Unit]

  def recoverPasswordFromToken(email: String, token: String, newPassword: String): F[Boolean]
}

class LiveAuth[F[_]: Async: Logger] private (
    users: Users[F],
    tokens: Tokens[F],
    emails: Emails[F]
) extends Auth[F] {
  def login(email: String, password: String): F[Option[User]] = for {
    maybeUser <- users.find(email)
    maybeValidatedUser <- maybeUser.filterA(user =>
      BCrypt.checkpwBool[F](password, PasswordHash[BCrypt](user.hashedPassword))
    )
  } yield maybeValidatedUser

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

  def sendPasswordRecoveryToken(email: String): F[Unit] =
    tokens.getToken(email).flatMap {
      case Some(token) => emails.sendPasswordRecoveryEmail(email, token)
      case None        => ().pure[F]
    }

  def recoverPasswordFromToken(email: String, token: String, newPassword: String): F[Boolean] =
    for {
      maybeUser    <- users.find(email)
      tokenIsValid <- tokens.checkToken(email, token)
      result <- (maybeUser, tokenIsValid) match {
        case (Some(user), true) => updateUser(user, newPassword).map(_.nonEmpty)
        case _                  => false.pure[F]
      }
    } yield result

    // private

  private def updateUser(user: User, newPassword: String): F[Option[User]] =
    for {
      hashedPassword <- BCrypt.hashpw[F](newPassword)
      updatedUser    <- users.update(user.copy(hashedPassword = hashedPassword))
    } yield updatedUser

}

object LiveAuth {
  def apply[F[_]: Async: Logger](
      users: Users[F],
      tokens: Tokens[F],
      emails: Emails[F]
  ): F[LiveAuth[F]] = {
    new LiveAuth[F](users, tokens, emails).pure[F]
  }
}
