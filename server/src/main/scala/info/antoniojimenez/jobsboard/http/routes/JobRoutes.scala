package info.antoniojimenez.jobsboard.http.routes

import io.circe.generic.auto.*
import org.http4s.circe.CirceEntityCodec.*

import cats.*
import cats.implicits.*
import cats.effect.*
import org.typelevel.log4cats.Logger
import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import scala.language.implicitConversions

import java.util.UUID
import info.antoniojimenez.jobsboard.domain.job.*
import info.antoniojimenez.jobsboard.domain.user.*
import info.antoniojimenez.jobsboard.domain.security.*
import info.antoniojimenez.jobsboard.domain.pagination.*
import info.antoniojimenez.jobsboard.http.responses.*
import info.antoniojimenez.jobsboard.core.*
import info.antoniojimenez.jobsboard.logging.syntax.*
import info.antoniojimenez.jobsboard.http.validation.syntax.*

import tsec.authentication.asAuthed
import tsec.authentication.SecuredRequestHandler

class JobRoutes[F[_]: Concurrent: Logger: SecuredHandler] private (jobs: Jobs[F])
    extends HttpValidationDsl[F] {

  object LimitQueryParam  extends OptionalQueryParamDecoderMatcher[Int]("limit")
  object OffsetQueryParam extends OptionalQueryParamDecoderMatcher[Int]("offset")

  // POST /jobs?limit=x&offset=y { filters } // TODO add query params and filters
  private val allJobsRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root :? LimitQueryParam(limit) +& OffsetQueryParam(offset) =>
      for {
        filter   <- req.as[JobFilter]
        jobsList <- jobs.all(filter, Pagination(limit, offset))
        resp     <- Ok(jobsList)
      } yield resp
  }

  // GET /jobs/uuid
  private val findJobRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root / UUIDVar(id) =>
    jobs.find(id).flatMap {
      case Some(job) => Ok(job)
      case None      => NotFound(FailureResponse(s"Job $id not found."))
    }
  }

  // POST /jobs { jobInfo }
  private val createJobRoute: AuthRoute[F] = { case req @ POST -> Root / "create" asAuthed _ =>
    req.request.validate[JobInfo] { jobInfo =>
      for {
        jobId <- jobs.create("TODO@rockthejvm.com", jobInfo)
        resp  <- Created(jobId)
      } yield resp
    }
  }

  // PUT /jobs/uuid { jobInfo }
  private val updateJobRoute: AuthRoute[F] = { case req @ PUT -> Root / UUIDVar(id) asAuthed user =>
    req.request.validate[JobInfo] { jobInfo =>
      jobs.find(id).flatMap {
        case None => NotFound(FailureResponse(s"Cannot delete job $id: not found"))
        case Some(job) if user.owns(job) || user.isAdmin =>
          jobs.update(id, jobInfo) *> Ok()
        case _ => Forbidden(FailureResponse(s"You can only delete your own jobs"))
      }
    }
  }

  // DELETE /jobs/uuid
  private val deleteJobRoute: AuthRoute[F] = {
    case req @ DELETE -> Root / UUIDVar(id) asAuthed user =>
      jobs.find(id).flatMap {
        case None => NotFound(FailureResponse(s"Cannot delete job $id: not found"))
        case Some(job) if user.owns(job) || user.isAdmin =>
          for {
            _    <- jobs.delete(id)
            resp <- Ok()
          } yield resp
        case _ => Forbidden(FailureResponse(s"You can only delete your own jobs"))
      }
  }

  val authedRoutes = SecuredHandler[F].liftService(
    createJobRoute.restrictedTo(allRoles) |+|
      updateJobRoute.restrictedTo(allRoles) |+|
      deleteJobRoute.restrictedTo(allRoles)
  )
  val unauthedRoutes = allJobsRoute <+> findJobRoute

  val routes = Router(
    "/jobs" -> (unauthedRoutes <+> authedRoutes)
  )
}

object JobRoutes {
  def apply[F[_]: Concurrent: Logger: SecuredHandler](jobs: Jobs[F]) =
    new JobRoutes[F](jobs)
}
