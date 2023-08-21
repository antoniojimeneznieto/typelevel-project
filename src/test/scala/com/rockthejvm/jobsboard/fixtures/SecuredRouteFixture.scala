package com.rockthejvm.jobsboard.fixtures

import cats.effect.*
import cats.data.*
import cats.implicits.*

import tsec.mac.jca.HMACSHA256
import tsec.authentication.IdentityStore
import tsec.authentication.JWTAuthenticator
import tsec.jws.mac.JWTMac
import scala.concurrent.duration.*

import com.rockthejvm.jobsboard.domain.user.*
import com.rockthejvm.jobsboard.domain.security.*
import org.http4s.*
import org.http4s.headers.*
import tsec.authentication.SecuredRequestHandler

trait SecuredRouteFixture extends UsersFixture {
  val mockedAuthenticator: Authenticator[IO] = {
    // key for hashing, identity store to retrieve users, jwt authenticator
    val key = HMACSHA256.unsafeGenerateKey
    val idStore: IdentityStore[IO, String, User] = (email: String) =>
      if (email == antonioEmail) OptionT.pure(Antonio)
      else if (email == riccardoEmail) OptionT.pure(Riccardo)
      else OptionT.none[IO, User]
    JWTAuthenticator.unbacked.inBearerToken(
      1.day,   // expiration of tokens
      None,    // max idle time (optional)
      idStore, // identity store
      key      //
    )
  }

  extension (r: Request[IO])
    def withBearerToken(a: JwtToken): Request[IO] =
      r.putHeaders {
        val jwtString = JWTMac.toEncodedString[IO, HMACSHA256](a.jwt)
        // Authorization: Bearer {jwt}
        Authorization(Credentials.Token(AuthScheme.Bearer, jwtString))
      }

  given securedHandler: SecuredHandler[IO] = SecuredRequestHandler(mockedAuthenticator)
}
