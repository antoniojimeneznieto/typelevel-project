package com.rockthejvm.foundations

import cats.effect.IOApp
import cats.effect.IO
import doobie.util.transactor.Transactor
import doobie.implicits.*
import cats.effect.kernel.MonadCancelThrow
import scala.concurrent.ExecutionContext
import doobie.util.ExecutionContexts
import doobie.hikari.HikariTransactor

object Doobie extends IOApp.Simple {

  case class Student(id: Int, name: String)

  // Defines the database and access data
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // JDBC connector
    "jdbc:postgresql://localhost:5432/demo", // database URL
    "docker", // user
    "docker" // password
  )

  // Query
  def findAllStudentsNames: IO[List[String]] = {
    val query = sql"select name from students".query[String] // query needs the type of each element that is searching
    val action = query.to[List] // Then we make it a List
    action.transact(xa)
  }

  // Write db
  def insertStudent(id: Int, name: String): IO[Int] = {
    val query = sql"insert into students(id, name) values ($id, $name)"
    val action = query.update.run // We use update to modify the database 
    action.transact(xa)
  }

  // query by fragments
  def findStudentsByInitial(letter: String): IO[List[Student]] = {
    val selectPart = fr"select id, name"
    val fromPart = fr"from students"
    val wherePart = fr"where left(name, 1) = $letter"

    val query = selectPart ++ fromPart ++ wherePart
    val action = query.query[Student].to[List]
    action.transact(xa)
  }

  // organize code
  trait Students[F[_]] {
    def findById(id: Int): F[Option[Student]]
    def findAll: F[List[Student]]
    def create(name: String): F[Int]
  }

  object Students {
    def make[F[_]: MonadCancelThrow](xa: Transactor[F]): Students[F] = new Students[F] {
      def findById(id: Int): F[Option[Student]] = 
        sql"select id, name from students where id = $id".query[Student].option.transact(xa)

      def findAll: F[List[Student]] = 
        sql"select id, name from students".query[Student].to[List].transact(xa)

      def create(name: String): F[Int] = 
        sql"insert into students(name) values ($name)".update.withUniqueGeneratedKeys[Int]("id").transact(xa)
    }
  }

  val postgresResource = for {
    ec <- ExecutionContexts.fixedThreadPool[IO](8)
    xa <- HikariTransactor.newHikariTransactor[IO](
        "org.postgresql.Driver",
        "jdbc:postgresql:demo",
        "docker",
        "docker",
        ec
    )
  } yield xa

  val smallProgram = postgresResource.use { xa =>
    val studentsRepo = Students.make[IO](xa)
    for {
        id <- studentsRepo.create("antonio")
        antonio <- studentsRepo.findById(id)
        _ <- IO.println(s"The first student of Rock The JVM is $antonio")
    } yield ()

  }

  override def run: IO[Unit] = smallProgram
}
