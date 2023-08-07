package com.rockthejvm.jobsboard.modules

import cats.implicits.*
import cats.effect.*
import com.rockthejvm.jobsboard.core.*
import doobie.*
import doobie.util.*
import doobie.hikari.HikariTransactor

final class Core[F[_]: Sync] private (val jobs: Jobs[F]) {}

// postgres -> jobs -> core -> httpApi -> app
object Core {
  def apply[F[_]: Async](xa: Transactor[F]): Resource[F, Core[F]] =
    Resource
      .eval(LiveJobs[F](xa))
      .map(jobs => new Core(jobs))
}
