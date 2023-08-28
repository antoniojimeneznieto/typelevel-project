package info.antoniojimenez.jobsboard.modules

import cats.*
import cats.data.*
import cats.implicits.*
import cats.effect.*
import cats.effect.kernel.Sync
import org.typelevel.log4cats.Logger
import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import info.antoniojimenez.jobsboard.config.*
import info.antoniojimenez.jobsboard.modules.*
import info.antoniojimenez.jobsboard.http.routes.*
import info.antoniojimenez.jobsboard.domain.security.*
import info.antoniojimenez.jobsboard.domain.auth.*
import info.antoniojimenez.jobsboard.domain.user.*
import info.antoniojimenez.jobsboard.core.*

import tsec.authentication.IdentityStore
import tsec.authentication.BackingStore
import tsec.common.SecureRandomId
import scala.concurrent.duration._

import tsec.authentication.AugmentedJWT
import tsec.mac.jca.HMACSHA256
import tsec.authentication.JWTAuthenticator
import tsec.passwordhashers.jca.BCrypt
import tsec.passwordhashers.PasswordHash
import cats.effect.kernel.Async
import tsec.authentication.SecuredRequestHandler

class HttpApi[F[_]: Concurrent: Logger] private (core: Core[F], authenticator: Authenticator[F]) {
  given securedHandler: SecuredHandler[F] = SecuredRequestHandler(authenticator)
  private val healthRoutes                = HealthRoutes[F].routes
  private val jobRoutes                   = JobRoutes[F](core.jobs).routes
  private val authRoutes                  = AuthRoutes[F](core.auth, authenticator).routes

  val endpoints = Router(
    "/api" -> (healthRoutes <+> jobRoutes <+> authRoutes)
  )
}

object HttpApi {
  def createAuthenticator[F[_]: Sync](
      users: Users[F],
      securityConfig: SecurityConfig
  ): F[Authenticator[F]] =
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
    val keyF =
      HMACSHA256.buildKey[F](securityConfig.secret.getBytes("UTF-8")) // TODO move to config

    // 4. authenticator
    for {
      key        <- keyF
      tokenStore <- tokenStoreF
    } yield JWTAuthenticator.backed.inBearerToken(
      expiryDuration = securityConfig.jwtExpiryDuration, // expiration of tokens
      maxIdle = None,                                    // max idle time (optional)
      identityStore = idStore,                           // identity store
      tokenStore = tokenStore,
      signingKey = key // hash key
    )

  def apply[F[_]: Async: Logger](
      core: Core[F],
      securityConfig: SecurityConfig
  ): Resource[F, HttpApi[F]] =
    Resource
      .eval(createAuthenticator(core.users, securityConfig))
      .map(authenticator => new HttpApi[F](core, authenticator))
}
