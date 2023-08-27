package com.rockthejvm.jobsboard.fixtures

import cats.effect.IO 
import com.rockthejvm.jobsboard.core.Users
import com.rockthejvm.jobsboard.domain.user.*

/*
scalarocks = $2a$10$hLJeMYjZTz7wx1CnVQB3MOLnPkt31UubfwrYEVCRvfZam7JfbQu1C
rockthejvm = $2a$10$CwRev.QE8pBqST/NWE7LNuRWuSFbaWaa3JRHyy1sz/uvuOf3nsMpG
riccardorulez = $2a$10$x6Ly5NFeuA9R3A/JTpS13.BKSo.hSfh.EgNLKFR582Klurykge5vW
simplepassword = $2a$10$h0Dx9KxmACTtJe/b5XpjTuq5K5xCX8mAiLxYWh7MasMx5w8VeT1De
riccardorocks = $2a$10$1QTSALSN9aoG0FIC0c.eRu6SixfDxwIxtgUPv3vGy9hofcraBT1Vq
 */

trait UsersFixture {

  val mockedUsers: Users[IO] = new Users[IO] {
    override def find(email: String): IO[Option[User]] =
      if (email == antonioEmail) IO.pure(Some(Antonio))
      else IO.pure(None)

    override def create(user: User): IO[String] = IO.pure(user.email)

    override def update(user: User): IO[Option[User]] = IO.pure(Some(user))

    override def delete(email: String): IO[Boolean] = IO.pure(true)
  }

  val Antonio = User(
    "antonio@gmail.com",
    "$2a$10$CwRev.QE8pBqST/NWE7LNuRWuSFbaWaa3JRHyy1sz/uvuOf3nsMpG",
    Some("Antonio"),
    Some("Jimenez"),
    Some("Swissborg"),
    Role.ADMIN
  )

  val antonioEmail    = Antonio.email
  val antonioPassword = "rockthejvm"

  val Riccardo = User(
    "riccardo@gmail.com",
    "$2a$10$x6Ly5NFeuA9R3A/JTpS13.BKSo.hSfh.EgNLKFR582Klurykge5vW",
    Some("Ricardo"),
    Some("cardin"),
    Some("Swissborg"),
    Role.RECRUITER
  )

  val riccardoEmail    = Riccardo.email
  val riccardoPassword = "riccardorulez"

  val NewUser = User(
    "newuser@gmail.com",
    "$2a$10$h0Dx9KxmACTtJe/b5XpjTuq5K5xCX8mAiLxYWh7MasMx5w8VeT1De",
    Some("John"),
    Some("Doe"),
    Some("Company"),
    Role.RECRUITER
  )

  val UpdatedRiccardo = User(
    "riccardo@gmail.com",
    "$2a$10$1QTSALSN9aoG0FIC0c.eRu6SixfDxwIxtgUPv3vGy9hofcraBT1Vq",
    Some("RICARDO"),
    Some("CARDIN"),
    Some("Adobe"),
    Role.RECRUITER
  )

  val NewUserAntonio = NewUserInfo(
    antonioEmail,
    antonioPassword,
    Some("Antonio"),
    Some("Jimenez"),
    Some("Swissborg")
  )

  val NewUserRiccardo = NewUserInfo(
    riccardoEmail,
    riccardoPassword,
    Some("Riccardo"),
    Some("cardin"),
    Some("Swissborg")
  )
}
