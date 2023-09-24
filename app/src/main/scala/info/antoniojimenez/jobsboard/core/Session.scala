package info.antoniojimenez.jobsboard.core

import cats.effect.IO
import tyrian.*
import tyrian.cmds.Logger
import org.scalajs.dom.document

import info.antoniojimenez.jobsboard.*
import scala.scalajs.js.Date
import info.antoniojimenez.jobsboard.common.*

final case class Session(email: Option[String] = None, token: Option[String] = None) {
  import Session.*

  def update(msg: Msg): (Session, Cmd[IO, Msg]) = msg match {
    case SetToken(email, token, isNewUser) =>
      (
        this.copy(email = Some(email), token = Some(token)),
        Commands.setAllSessionCookies(email, token, isNewUser)
      )
  }

  def initCmd: Cmd[IO, Msg] = {
    val maybeCommand = for {
      email <- getCookie(Constants.cookies.email)
      token <- getCookie(Constants.cookies.token)
    } yield Cmd.Emit(SetToken(email, token, isNewUser = false))

    maybeCommand.getOrElse(Cmd.None)
  }
}

object Session {
  trait Msg                                                                     extends App.Msg
  case class SetToken(email: String, token: String, isNewUser: Boolean = false) extends Msg

  object Commands {
    def setSessionCookie(name: String, value: String, isFresh: Boolean = false): Cmd[IO, Msg] =
      Cmd.SideEffect[IO] {
        if (getCookie(name).isEmpty || isFresh) {
          document.cookie =
            s"$name=$value;expires=${new Date(Date.now() + Constants.cookies.duration)};path=/"
        }
      }

    def setAllSessionCookies(email: String, token: String, isFresh: Boolean = false): Cmd[IO, Msg] =
      setSessionCookie(Constants.cookies.email, email, isFresh) |+|
        setSessionCookie(Constants.cookies.token, token, isFresh)

    def clearSessionCookie(name: String): Cmd[IO, Msg] =
      Cmd.SideEffect[IO] {
        document.cookie = s"$name=;expires=${new Date(0)};path=/" 
      }

    def clearAllSessionCookie(): Cmd[IO, Msg] =
      clearSessionCookie(Constants.cookies.email) |+|
        clearSessionCookie(Constants.cookies.token)
  }

  private def getCookie(name: String): Option[String] =
    document.cookie
      .split(";")
      .map(_.trim)
      .find(_.startsWith(s"$name="))
      .map(_.split("="))
      .map(_(1))
}
