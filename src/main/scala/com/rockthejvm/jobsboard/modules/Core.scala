package com.rockthejvm.jobsboard.modules

import cats.implicits.*
import cats.effect.*
import com.rockthejvm.jobsboard.core.*
import doobie.*
import doobie.util.*
import doobie.hikari.HikariTransactor

final class Core[F[_]: Sync] private (val jobs: Jobs[F]) {
  
}

// postgres -> jobs -> core -> httpApi -> app
object Core {
  def postgresResource[F[_]: Async]: Resource[F, HikariTransactor[F]] = for {
    ec <- ExecutionContexts.fixedThreadPool(32)
    xa <- HikariTransactor.newHikariTransactor[F](
      "org.postgresql.Driver",
      "jdbc:postgresql:board", // TODO move to config
      "docker",
      "docker",
      ec
    )
  } yield xa

  def apply[F[_]: Async]: Resource[F, Core[F]] = 
    postgresResource[F]
      .evalMap(postgres => LiveJobs[F](postgres))
      .map(jobs => new Core(jobs))
}
