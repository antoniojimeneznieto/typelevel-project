package com.rockthejvm.jobsboard.fixtures

import com.rockthejvm.jobsboard.domain.user.*

trait UsersFixture {
  val Antonio = User(
    "antonio@gmail.com",
    "rockthejvm",
    Some("Antonio"),
    Some("Jimenez"),
    Some("Swissborg"),
    Role.ADMIN
  )

  val antonioEmail = Antonio.email

  val Riccardo = User(
    "riccardo@gmail.com",
    "riccardorulez",
    Some("Ricardo"),
    Some("cardin"),
    Some("Swissborg"),
    Role.RECRUITER
  )

  val riccardoEmail = Riccardo.email

  val NewUser = User(
    "newuser@gmail.com",
    "simplepassword",
    Some("John"),
    Some("Doe"),
    Some("Company"),
    Role.RECRUITER
  )

  val UpdatedRiccardo = User(
    "riccardo@gmail.com",
    "riccardorocks",
    Some("RICARDO"),
    Some("CARDIN"),
    Some("Adobe"),
    Role.RECRUITER
  )
}
