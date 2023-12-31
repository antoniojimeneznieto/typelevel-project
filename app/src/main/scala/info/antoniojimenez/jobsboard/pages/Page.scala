package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import cats.effect.IO

import info.antoniojimenez.jobsboard.*

object Page {
  trait Msg
  enum StatusKind {
    case SUCCESS, ERROR, LOADING
  }
  case class Status(messages: String, kind: StatusKind)

  object Urls {
    val LOGIN            = "/login"
    val SIGNUP           = "/signup"
    val FORGOT_PASSWORD  = "/forgotpassword"
    val RECOVER_PASSWORD = "/recoverpassword"
    val JOBS             = "/jobs"
    val EMPTY            = ""
    val HOME             = "/"
    val HASH             = "#"
  }

  import Urls.*
  def get(location: String) = location match {
    case `LOGIN`                   => LoginPage()
    case `SIGNUP`                  => SignUpPage()
    case `FORGOT_PASSWORD`         => ForgotPasswordPage()
    case `RECOVER_PASSWORD`        => RecoverPasswordPage()
    case `EMPTY` | `HOME` | `JOBS` => JobListPage()
    case s"/jobs/$id"              => JobPage(id)
    case _                         => NotFoundPage()
  }
}

abstract class Page {
  // API
  // send a command upon instantiating
  def initCmd: Cmd[IO, App.Msg]
  // update
  def update(msg: App.Msg): (Page, Cmd[IO, App.Msg])
  // render
  def view(): Html[App.Msg]

}
