package info.antoniojimenez.jobsboard.playground

import cats.effect.*
import doobie.*
import doobie.implicits.*
import doobie.util.*
import doobie.hikari.HikariTransactor
import info.antoniojimenez.jobsboard.domain.job.*
import info.antoniojimenez.jobsboard.core.LiveJobs
import scala.io.StdIn

import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger


object JobsPlayground extends IOApp.Simple {

  val postgresResource: Resource[IO, HikariTransactor[IO]] = for {
    ec <- ExecutionContexts.fixedThreadPool(32)
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql:board",
      "docker",
      "docker",
      ec
    )
  } yield xa

  val jobInfo = JobInfo.minimal(
    company = "Rock the JVM",
    title = "Software Engineer",
    description = "Best job ever",
    externalUrl = "rockthejvm.com",
    remote = true,
    location = "Anywhere"
  )

  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]
  override def run: IO[Unit] = postgresResource.use { xa =>
    for {
      jobs      <- LiveJobs[IO](xa)
      _         <- IO.println("Ready. Next...") *> IO(StdIn.readLine)
      id        <- jobs.create("antonio.jimenez.nieto@gmail.com", jobInfo)
      _         <- IO.println(s"The id is $id Next...") *> IO(StdIn.readLine)
      list      <- jobs.all()
      _         <- IO.println(s"All jobs: $list. Next...") *> IO(StdIn.readLine)
      _         <- jobs.update(id, jobInfo.copy(title = "Software rockstar"))
      newJob    <- jobs.find(id)
      _         <- IO.println(s"New job: $newJob. Next...") *> IO(StdIn.readLine)
      _         <- jobs.delete(id)
      listAfter <- jobs.all()
      _         <- IO.println(s"Deleted job. List now: $listAfter. Next...") *> IO(StdIn.readLine)
    } yield ()
  }
}

/* 
    curl -v -X POST localhost:4041/api/jobs                         -- find all jobs
    curl -v -X GET localhost:4041/api/jobs/<id>                     -- find a specific job by <id>
    curl -v -X DELETE localhost:4041/api/jobs/<id?                  -- delete a specific job by <id>
    curl -v -X POST -H "Content-Type: application/json"             -- create a job with the .json data from the specify file
                    -d @src/main/resources/payloads/jobinfo.json
                    localhost:4041/api/jobs/create
    curl -v -X PUT -H "Content-Type: application/json"              -- update a job with <id> with the .json data from the specify file
                   -d @src/main/resources/payloads/jobinfo.json
                   localhost:4041/api/jobs/<id> 

*/
