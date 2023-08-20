package com.rockthejvm.jobsboard.modules

import cats.implicits.*
import cats.effect.*
import com.rockthejvm.jobsboard.core.*
import doobie.*
import doobie.util.*
import doobie.hikari.HikariTransactor
import org.typelevel.log4cats.Logger
import com.rockthejvm.jobsboard.config.*

final class Core[F[_]: Sync] private (val jobs: Jobs[F], val auth: Auth[F])

// postgres -> jobs -> core -> httpApi -> app
object Core {
  def apply[F[_]: Async: Logger](
      xa: Transactor[F]
  )(securityConfig: SecurityConfig): Resource[F, Core[F]] = {
    val coreF = for {
      jobs  <- LiveJobs[F](xa)
      users <- LiveUsers[F](xa)
      auth  <- LiveAuth[F](users)(securityConfig)
    } yield new Core(jobs, auth)

    Resource.eval(coreF)
  }
}
