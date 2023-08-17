package com.rockthejvm.jobsboard.domain

import org.http4s.Response
import tsec.authentication.*
import tsec.mac.jca.HMACSHA256
import com.rockthejvm.jobsboard.domain.user.*

object security {
  type Crypto = HMACSHA256
  type JwtToken = AugmentedJWT[Crypto, String]
  type Authenticator[F[_]] = JWTAuthenticator[F, String, User, Crypto]
  type AuthRoute[F[_]] = PartialFunction[SecuredRequest[F, User, JwtToken], F[Response[F]]]
}
