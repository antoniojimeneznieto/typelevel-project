package info.antoniojimenez.jobsboard.core

import cats.effect.IO
import tyrian.*
import tyrian.cmds.Logger

import info.antoniojimenez.jobsboard.*

final case class Session(email: Option[String] = None, token: Option[String] = None) {
  import Session.*

  def update(msg: Msg): (Session, Cmd[IO, Msg]) = msg match {
    case SetToken(email, token) =>
      (
        this.copy(email = Some(email), token = Some(token)),
        Logger.consoleLog[IO](s"Setting user session: $email - $token")
      )
  }

  def initCmd: Cmd[IO, Msg] = Logger.consoleLog[IO]("Starting session monitoring")
}

object Session {
  trait Msg extends App.Msg
  case class SetToken(email: String, token: String) extends Msg
}
