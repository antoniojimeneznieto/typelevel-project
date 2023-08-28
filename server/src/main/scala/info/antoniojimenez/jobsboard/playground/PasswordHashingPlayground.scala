package info.antoniojimenez.jobsboard.playground

import cats.effect.IOApp
import cats.effect.IO
import tsec.passwordhashers.jca.BCrypt
import tsec.passwordhashers.PasswordHash

object PasswordHashingPlayground extends IOApp.Simple {

  override def run: IO[Unit] =
    BCrypt.hashpw[IO]("rockthejvm").flatMap(IO.println) *>
      BCrypt.hashpw[IO]("riccardorulez").flatMap(IO.println) *>
      BCrypt.hashpw[IO]("simplepassword").flatMap(IO.println) *>
      BCrypt.hashpw[IO]("riccardorocks").flatMap(IO.println) *>
      BCrypt.checkpwBool[IO](
        "rockthejvm",
        PasswordHash[BCrypt]("$2a$10$CwRev.QE8pBqST/NWE7LNuRWuSFbaWaa3JRHyy1sz/uvuOf3nsMpG")
      ).flatMap(IO.println)

}
