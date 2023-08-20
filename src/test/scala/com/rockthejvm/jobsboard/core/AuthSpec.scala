package com.rockthejvm.jobsboard.core

import cats.effect.*
import cats.data.OptionT
import org.scalatest.freespec.AsyncFreeSpec
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import tsec.mac.jca.HMACSHA256
import tsec.authentication.IdentityStore
import tsec.authentication.JWTAuthenticator
import scala.concurrent.duration.*
import tsec.passwordhashers.jca.BCrypt
import tsec.passwordhashers.PasswordHash

import com.rockthejvm.jobsboard.fixtures.*
import com.rockthejvm.jobsboard.config.*
import com.rockthejvm.jobsboard.domain.user.*
import com.rockthejvm.jobsboard.domain.security.*
import com.rockthejvm.jobsboard.domain.auth.*
import com.rockthejvm.jobsboard.domain.user

class AuthSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers with UsersFixture {

  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  private val mockedUsers: Users[IO] = new Users[IO] {
    override def find(email: String): IO[Option[User]] =
      if (email == antonioEmail) IO.pure(Some(Antonio))
      else IO.pure(None)

    override def create(user: User): IO[String] = IO.pure(user.email)

    override def update(user: User): IO[Option[User]] = IO.pure(Some(user))

    override def delete(email: String): IO[Boolean] = IO.pure(true)
  }

  val mockedConfig = SecurityConfig("secret", 1.day)

  // val mockedAuthenticator: Authenticator[IO] = {
  //   // key for hashing, identity store to retrieve users, jwt authenticator
  //   val key = HMACSHA256.unsafeGenerateKey
  //   val idStore: IdentityStore[IO, String, User] = (email: String) =>
  //     if (email == antonioEmail) OptionT.pure(Antonio)
  //     else if (email == riccardoEmail) OptionT.pure(Riccardo)
  //     else OptionT.none[IO, User]
  //   JWTAuthenticator.unbacked.inBearerToken(
  //     1.day,   // expiration of tokens
  //     None,    // max idle time (optional)
  //     idStore, // identity store
  //     key      //
  //   )
  // }

  "Auth 'algebra'" - {
    "login should return None if the user doesn't exist" in {
      val program = for {
        auth       <- LiveAuth[IO](mockedUsers)(mockedConfig)
        maybeToken <- auth.login("user@gmail.com", "password")
      } yield maybeToken

      program.asserting(_ shouldBe None)
    }

    "login should return None if the user exists but the password is wrong" in {
      val program = for {
        auth       <- LiveAuth[IO](mockedUsers)(mockedConfig)
        maybeToken <- auth.login("antonio@gmail.com", "wrongPassword")
      } yield maybeToken

      program.asserting(_ shouldBe None)
    }

    "login should return a token if the user exists and the password is correct" in {
      val program = for {
        auth       <- LiveAuth[IO](mockedUsers)(mockedConfig)
        maybeToken <- auth.login(antonioEmail, "rockthejvm")
      } yield maybeToken

      program.asserting(_ shouldBe defined)
    }

    "signing up should not create a user with an existing email" in {
      val program = for {
        auth <- LiveAuth[IO](mockedUsers)(mockedConfig)
        maybeUser <- auth.signUp(
          NewUserInfo(
            antonioEmail,
            "somePassword",
            Some("Antonio"),
            Some("Whatever"),
            Some("Other company")
          )
        )
      } yield maybeUser

      program.asserting(_ shouldBe None)
    }

    "signing up should create a completely new user" in {
      val program = for {
        auth <- LiveAuth[IO](mockedUsers)(mockedConfig)
        maybeUser <- auth.signUp(
          NewUserInfo(
            "boby@gmail.com",
            "somePassword",
            Some("Bob"),
            Some("Bones"),
            Some("the ultimate company")
          )
        )
      } yield maybeUser

      program.asserting {
        case Some(user) =>
          user.email shouldBe "boby@gmail.com"
          user.firstName shouldBe Some("Bob")
          user.lastName shouldBe Some("Bones")
          user.company shouldBe Some("the ultimate company")
          user.role shouldBe Role.RECRUITER
        case _ =>
          fail()
      }
    }

    "changePassword should return Right(None) if the user doesn't exist" in {
      val program = for {
        auth   <- LiveAuth[IO](mockedUsers)(mockedConfig)
        result <- auth.changePassword("alice@gmail.com", NewPasswordInfo("oldpass", "newpass"))
      } yield result

      program.asserting(_ shouldBe Right(None))
    }

    "changePassword should return Left with an error if the user exist and the password is incorrect" in {
      val program = for {
        auth   <- LiveAuth[IO](mockedUsers)(mockedConfig)
        result <- auth.changePassword(antonioEmail, NewPasswordInfo("oldpass", "newpass"))
      } yield result

      program.asserting(_ shouldBe Left("Invalid password"))
    }

    "changePassword should correctly change password if all details are correct" in {
      val program = for {
        auth   <- LiveAuth[IO](mockedUsers)(mockedConfig)
        result <- auth.changePassword(antonioEmail, NewPasswordInfo("rockthejvm", "scalarocks"))
        isNicePassword <- result match
          case Right(Some(user)) =>
            BCrypt.checkpwBool[IO](
              "scalarocks",
              PasswordHash[BCrypt](user.hashedPassword)
            )
          case _ => IO.pure(false)

      } yield isNicePassword

      program.asserting(_ shouldBe true)
    }

  }

}
