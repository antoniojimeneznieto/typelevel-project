package info.antoniojimenez.jobsboard.http.routes

import cats.effect.*
import cats.implicits.*
import io.circe.generic.auto.*
import org.http4s.*
import org.http4s.dsl.*
import org.http4s.implicits.*
import org.http4s.circe.CirceEntityCodec.*
import org.scalatest.freespec.AsyncFreeSpec
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.matchers.should.Matchers

import info.antoniojimenez.jobsboard.fixtures.*
import info.antoniojimenez.jobsboard.core.*
import info.antoniojimenez.jobsboard.domain.job.*
import info.antoniojimenez.jobsboard.domain.pagination.*

import java.util.UUID
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class JobRoutesSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with Http4sDsl[IO]
    with JobFixture
    with SecuredRouteFixture {

  ////////////////////////////////////////////////////////////////////////////////
  // prep
  ////////////////////////////////////////////////////////////////////////////////
  val jobs: Jobs[IO] = new Jobs[IO] {
    override def create(ownerEmail: String, jobInfo: JobInfo): IO[UUID] =
      IO.pure(NewJobUuid)

    override def all(): IO[List[Job]] =
      IO.pure(List(AwesomeJob))

    override def all(filter: JobFilter, pagination: Pagination): IO[List[Job]] =
      if (filter.remote) IO.pure(List())
      else IO.pure(List(AwesomeJob))

    override def find(id: UUID): IO[Option[Job]] =
      if (id == AwesomeJobUuid)
        IO.pure(Some(AwesomeJob))
      else
        IO.pure(None)

    override def update(id: UUID, jobInfo: JobInfo): IO[Option[Job]] =
      if (id == AwesomeJobUuid)
        IO.pure(Some(UpdatedAwesomeJob))
      else
        IO.pure(None)

    override def delete(id: UUID): IO[Int] =
      if (id == AwesomeJobUuid) IO.pure(1) else IO.pure(0)
  }

  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]
  // this is what we are testing
  val jobRoutes: HttpRoutes[IO] = JobRoutes[IO](jobs).routes

  ////////////////////////////////////////////////////////////////////////////////
  // tests
  ////////////////////////////////////////////////////////////////////////////////

  "JobRoutes" - {
    "should return a job with a given id" in {
      // code under test
      for {
        // simulate an HTTP request
        response <- jobRoutes.orNotFound.run(
          Request(method = Method.GET, uri = uri"/jobs/843df718-ec6e-4d49-9289-f799c0f40064")
        )
        // get the HTTP response
        retrieved <- response.as[Job]
      } yield {
        // make some assertion
        response.status shouldBe Status.Ok
        retrieved shouldBe AwesomeJob
      }
    }

    "should return all jobs" in {
      for {
        response <- jobRoutes.orNotFound.run(
          Request(method = Method.POST, uri = uri"/jobs/")
            .withEntity(JobFilter()) // empty filter
        )
        retrieved <- response.as[List[Job]]
      } yield {
        response.status shouldBe Status.Ok
        retrieved shouldBe List(AwesomeJob)
      }
    }

    "should return all jobs that satisfy a filter" in {
      for {
        response <- jobRoutes.orNotFound.run(
          Request(method = Method.POST, uri = uri"/jobs/")
            .withEntity(JobFilter(remote = true)) // empty filter
        )
        retrieved <- response.as[List[Job]]
      } yield {
        response.status shouldBe Status.Ok
        retrieved shouldBe List()
      }
    }

    "should create a new jobs" in {
      for {
        jwtToken <- mockedAuthenticator.create(antonioEmail)
        response <- jobRoutes.orNotFound.run(
          Request[IO](method = Method.POST, uri = uri"/jobs/create")
            .withEntity(AwesomeJob.jobInfo)
            .withBearerToken(jwtToken)
        )
        retrieved <- response.as[UUID]
      } yield {
        response.status shouldBe Status.Created
        retrieved shouldBe NewJobUuid
      }
    }

    "should only update just a job that exisits" in {
      for {
        jwtToken <- mockedAuthenticator.create(antonioEmail)
        responseOk <- jobRoutes.orNotFound.run(
          Request[IO](method = Method.PUT, uri = uri"/jobs/843df718-ec6e-4d49-9289-f799c0f40064")
            .withEntity(UpdatedAwesomeJob.jobInfo)
            .withBearerToken(jwtToken)
        )
        responseInvalid <- jobRoutes.orNotFound.run(
          Request[IO](method = Method.PUT, uri = uri"/jobs/843df718-ec6e-4d49-9289-000000000000")
            .withEntity(UpdatedAwesomeJob.jobInfo)
            .withBearerToken(jwtToken)
        )
      } yield {
        responseOk.status shouldBe Status.Ok
        responseInvalid.status shouldBe Status.NotFound
      }
    }

    "should forbid the update of a job that the JWT token doesn't owns" in {
      for {
        jwtToken <- mockedAuthenticator.create("somebody@gmail.com")
        response <- jobRoutes.orNotFound.run(
          Request[IO](method = Method.PUT, uri = uri"/jobs/843df718-ec6e-4d49-9289-f799c0f40064")
            .withEntity(UpdatedAwesomeJob.jobInfo)
            .withBearerToken(jwtToken)
        )
      } yield {
        response.status shouldBe Status.Unauthorized
      }
    }

    "should only delete a job that exisits" in {
      for {
        jwtToken <- mockedAuthenticator.create(antonioEmail)
        responseOk <- jobRoutes.orNotFound.run(
          Request(method = Method.DELETE, uri = uri"/jobs/843df718-ec6e-4d49-9289-f799c0f40064")
            .withBearerToken(jwtToken)
        )
        responseInvalid <- jobRoutes.orNotFound.run(
          Request(method = Method.DELETE, uri = uri"/jobs/843df718-ec6e-4d49-9289-000000000000")
            .withBearerToken(jwtToken)
        )
      } yield {
        responseOk.status shouldBe Status.Ok
        responseInvalid.status shouldBe Status.NotFound
      }
    }

  }

}
