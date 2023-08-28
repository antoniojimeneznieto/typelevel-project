package info.antoniojimenez.jobsboard.core

import cats.effect.*
import org.scalatest.freespec.AsyncFreeSpec
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import scala.concurrent.duration._

import info.antoniojimenez.jobsboard.fixtures.*
import info.antoniojimenez.jobsboard.domain.user.*
import info.antoniojimenez.jobsboard.config.*

class TokensSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with DoobieSpec
    with UsersFixture {

  val initScript: String   = "sql/recoverytokens.sql"
  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  "Tokens 'algebra'" - {
    "should not create a new token for a non-existing user" in {
      transactor.use { xa =>
        val program = for {
          tokens <- LiveTokens[IO](mockedUsers)(xa, TokenConfig(100000000L))
          token  <- tokens.getToken("somebody@someemail.com")
        } yield token

        program.asserting(_ shouldBe None)
      }
    }

    "should not validate expired tokens" in {
      transactor.use { xa =>
        val program = for {
          tokens     <- LiveTokens[IO](mockedUsers)(xa, TokenConfig(100L))
          maybeToken <- tokens.getToken(antonioEmail)
          _          <- IO.sleep(500.millis)
          isTokenValid <- maybeToken match {
            case Some(token) => tokens.checkToken(antonioEmail, token)
            case None        => IO.pure(false)
          }
        } yield isTokenValid

        program.asserting(_ shouldBe false)
      }
    }

    "should validate tokens that have not expired yet" in {
      transactor.use { xa =>
        val program = for {
          tokens     <- LiveTokens[IO](mockedUsers)(xa, TokenConfig(1000000L))
          maybeToken <- tokens.getToken(antonioEmail)
          isTokenValid <- maybeToken match {
            case Some(token) => tokens.checkToken(antonioEmail, token)
            case None        => IO.pure(false)
          }
        } yield isTokenValid

        program.asserting(_ shouldBe true)
      }
    }

    "should only validate tokens for the user that generated them" in {
      transactor.use { xa =>
        val program = for {
          tokens     <- LiveTokens[IO](mockedUsers)(xa, TokenConfig(1000000L))
          maybeToken <- tokens.getToken(antonioEmail)
          isAntonioTokenValid <- maybeToken match {
            case Some(token) => tokens.checkToken(antonioEmail, token)
            case None        => IO.pure(false)
          }
          isOtherTokenValid <- maybeToken match {
            case Some(token) => tokens.checkToken("someoneelse@gmail.com", token)
            case None        => IO.pure(false)
          }
        } yield (isAntonioTokenValid, isOtherTokenValid)

        program.asserting { case (isAntonioTokenValid, isOtherTokenValid) =>
          isAntonioTokenValid shouldBe true
          isOtherTokenValid shouldBe false
        }
      }
    }

  }

}
