package info.antoniojimenez.jobsboard

import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.CORS
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import cats.*
import cats.implicits._
import cats.effect.IO
import cats.effect.IOApp

import info.antoniojimenez.jobsboard.modules.*
import info.antoniojimenez.jobsboard.config.*

import pureconfig.ConfigSource
import pureconfig.error.ConfigReaderException
import info.antoniojimenez.jobsboard.config.syntax.loadF

object Application extends IOApp.Simple {

  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]
  override def run: IO[Unit] = ConfigSource.default.loadF[IO, AppConfig].flatMap {
    case AppConfig(postgresConfig, emberConfig, securityConfig, tokenConfig, emailServiceConfig) =>
      val appResource = for {
        xa      <- Database.makePostgresResource[IO](postgresConfig)
        core    <- Core[IO](xa, tokenConfig, emailServiceConfig)
        httpApi <- HttpApi[IO](core, securityConfig)
        server <- EmberServerBuilder
          .default[IO]
          .withHost(emberConfig.host)
          .withPort(emberConfig.port)
          .withHttpApp(CORS(httpApi.endpoints).orNotFound) // TODO remove when deploying
          .build
      } yield server

      appResource.use(_ => IO.println("Server ready!") *> IO.never)
  }

}
