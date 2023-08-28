package info.antoniojimenez.foundations

import cats._
import cats.implicits._

import cats.effect.*

import io.circe.generic.auto.*
import io.circe.syntax.*

import org.http4s.*
import org.http4s.dsl.*
import org.http4s.circe.*
import org.http4s.headers.*
import org.http4s.dsl.impl.*
import org.http4s.server.*
import org.typelevel.ci.CIString
import org.http4s.ember.server.EmberServerBuilder

import java.util.UUID
object Http4s extends IOApp.Simple {

  type Student = String
  case class Instructor(firstName: String, lastName: String)
  case class Course(
      id: String,
      title: String,
      year: Int,
      students: List[Student],
      instructorName: String)

  object CourseRepository {
    // a "database"
    val catsEffectCourse = Course(
      "3aafdd3a-e56c-46b5-8785-a0c2f10767e0",
      "The Ultimate Scala course",
      2023,
      List("Antonio", "Theo"),
      "Martin Odersky"
    )

    private val courses: Map[String, Course] = Map(catsEffectCourse.id -> catsEffectCourse)

    // API
    def findCoursesById(courseId: UUID): Option[Course] = courses.get(courseId.toString())

    def findCoursesByInstructor(name: String): List[Course] =
      courses.values.filter(_.instructorName == name).toList

  }

  // essential REST endpoints
  // GET localhost:8080/courses?instructor=Martin%20Odersky&year=2023
  // GET localhost:8080/courses/3aafdd3a-e56c-46b5-8785-a0c2f10767e0/students

  object InstructorQueryParamMatcher extends QueryParamDecoderMatcher[String]("instructor")
  object YearQueryParamMatcher extends OptionalValidatingQueryParamDecoderMatcher[Int]("year")

  def courseRoutes[F[_]: Applicative: Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl.*

    HttpRoutes.of[F] {
      case GET -> Root / "courses" :? InstructorQueryParamMatcher(
            instructor) +& YearQueryParamMatcher(maybeYear) =>
        val courses = CourseRepository.findCoursesByInstructor(instructor)
        maybeYear match {
          case Some(y) =>
            y.fold(
              _ => BadRequest("Parameter 'year' is invalid"),
              year => Ok(courses.filter(_.year == year).asJson)
            )
          case None => Ok(courses.asJson)
        }
      case GET -> Root / "courses" / UUIDVar(courseId) / "students" =>
        CourseRepository.findCoursesById(courseId).map(_.students) match {
          case Some(students) =>
            Ok(students.asJson, Header.Raw(CIString("My-custom-header"), "rockthejvm"))
          case None => NotFound(s"No course with $courseId was found")
        }
    }
  }

  def healthEndpoint[F[_]: Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl.*
    HttpRoutes.of[F] { case GET -> Root / "health" => Ok("All going great!") }
  }

  def allRoutes[F[_]: Monad]: HttpRoutes[F] = courseRoutes[F] <+> healthEndpoint[F]

  def routerWithPathPrefixes = Router(
    "/api" -> courseRoutes[IO],
    "/private" -> healthEndpoint[IO]
  ).orNotFound
  override def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHttpApp(routerWithPathPrefixes)
      .build
      .use(_ => IO.println("Server ready!") *> IO.never)

}
