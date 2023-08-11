package com.rockthejvm.jobsboard.playground

import cats.effect.IOApp
import cats.effect.IO
import tsec.passwordhashers.jca.BCrypt
import tsec.passwordhashers.PasswordHash

object PasswordHashingPlayground extends IOApp.Simple {

  override def run: IO[Unit] =
    BCrypt.hashpw[IO]("scalarocks").flatMap(IO.println) *> BCrypt.checkpwBool[IO](
      "scalarocks",
      PasswordHash[BCrypt]("$2a$10$NFMNEi9HM90FjSjhJU7XFO7oHZh.mN/LMgr6EcYrqN4K6DYCSslCO")
    ).flatMap(IO.println)

}
