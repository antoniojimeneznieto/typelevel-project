package com.rockthejvm.jobsboard.core

import cats.effect.*
import doobie.postgres.implicits.*
import doobie.implicits.*
import org.scalatest.freespec.AsyncFreeSpec
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import com.rockthejvm.jobsboard.fixtures.*
import com.rockthejvm.jobsboard.domain.user.*
import org.scalatest.Inside
import org.postgresql.util.PSQLException

class UsersSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with Inside
    with DoobieSpec
    with UsersFixture {
  override val initScript: String = "sql/users.sql"
  given logger: Logger[IO]        = Slf4jLogger.getLogger[IO]

  "Users 'algebra'" - {
    "should retrive a user by email" in {
      transactor.use { xa =>
        val program = for {
          users     <- LiveUsers[IO](xa)
          retrieved <- users.find("riccardo@gmail.com")
        } yield retrieved

        program.asserting(_ shouldBe Some(Riccardo))
      }
    }

    "should return None if the email doesn't exist" in {
      transactor.use { xa =>
        val program = for {
          users     <- LiveUsers[IO](xa)
          retrieved <- users.find("notfound@gmail.com")
        } yield retrieved

        program.asserting(_ shouldBe None)
      }
    }

    "should create a new user" in {
      transactor.use { xa =>
        val program = for {
          users  <- LiveUsers[IO](xa)
          userId <- users.create(NewUser)
        } yield userId

        program.asserting(_ shouldBe NewUser.email)

        val query = transactor.use { xa =>
          sql"SELECT * FROM users WHERE email = ${NewUser.email}"
            .query[User]
            .option
            .transact(xa)
        }

        query.asserting(_ shouldBe Some(NewUser))

      }
    }

    "should fail creating a new user if the email already exists" in {
      transactor.use { xa =>
        val program = for {
          users  <- LiveUsers[IO](xa)
          userId <- users.create(Antonio).attempt
        } yield userId

        program.asserting { outcome =>
          inside(outcome) {
            case Left(e) => e shouldBe a[PSQLException]
            case _       => fail()
          }
        }
      }
    }

    "should return None when updating a user that does not exist" in {
      transactor.use { xa =>
        val program = for {
          users     <- LiveUsers[IO](xa)
          maybeUser <- users.update(NewUser)
        } yield maybeUser

        program.asserting(_ shouldBe None)
      }
    }

    "should update an existing user" in {
      transactor.use { xa =>
        val program = for {
          users     <- LiveUsers[IO](xa)
          maybeUser <- users.update(UpdatedRiccardo)
        } yield maybeUser

        program.asserting(_ shouldBe Some(UpdatedRiccardo))
      }
    }

    "should delete a user" in {
      transactor.use { xa =>
        val program = for {
          users  <- LiveUsers[IO](xa)
          result <- users.delete("antonio@gmail.com")
        } yield result

        program.asserting(_ shouldBe true)

        val query = transactor.use { xa =>
          sql"SELECT * FROM users WHERE email = ${NewUser.email}"
            .query[User]
            .option
            .transact(xa)
        }

        query.asserting(_ shouldBe None)
      }
    }

    "should not delete a user that does not exist" in {
      transactor.use { xa =>
        val program = for {
          users  <- LiveUsers[IO](xa)
          result <- users.delete("nobody@gmail.com")
        } yield result

        program.asserting(_ shouldBe false)
      }
    }

  }
}
