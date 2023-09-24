package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import tyrian.http.*
import cats.effect.*

import io.circe.syntax.*
import io.circe.parser.*
import io.circe.generic.auto.*

import info.antoniojimenez.jobsboard.common.Constants
import info.antoniojimenez.jobsboard.domain.auth.LoginInfo
import info.antoniojimenez.jobsboard.common.Endpoint
import tyrian.cmds.Logger
import info.antoniojimenez.jobsboard.core.Session

import info.antoniojimenez.jobsboard.*

final case class LoginPage(
    email: String = "",
    password: String = "",
    status: Option[Page.Status] = None
) extends Page {
  import LoginPage.*

  def initCmd: Cmd[IO, App.Msg] =
    Cmd.None

  def update(msg: App.Msg): (Page, Cmd[IO, App.Msg]) = msg match {
    case UpdateEmail(email)       => (this.copy(email = email), Cmd.None)
    case UpdatePassword(password) => (this.copy(password = password), Cmd.None)
    case AttemptLogin =>
      if (!email.matches(Constants.emailRegex))
        (setErrorStatus("Invalid email"), Cmd.None)
      else if (password.isEmpty())
        (setErrorStatus("Please enter a passwrod"), Cmd.None)
      else (this, Commands.login(LoginInfo(email, password)))
    case LoginError(error) =>
      (setErrorStatus(error), Cmd.None)
    case LoginSuccess(token) =>
      (setSuccessStatus("Success!"), Cmd.emit(Session.SetToken(email, token, isNewUser = true)))
    case _ => (this, Cmd.None)
  }

  def view(): Html[App.Msg] =
    div(`class` := "form-section")(
      div(`class` := "top-section")(
        h1("Log In")
      ),
      form(
        name    := "signin",
        `class` := "form",
        onEvent(
          "submit",
          e => {
            e.preventDefault()
            NoOp
          }
        )
      )(
        // inputs
        renderInput("Email", "email", "text", true, UpdateEmail(_)),
        renderInput("Password", "password", "password", true, UpdatePassword(_)),
        // button
        button(`type` := "button", onClick(AttemptLogin))("Log In")
      ),
      status.map(s => div(s.messages)).getOrElse(div())
    )

    // private API

  // UI
  private def renderInput(
      name: String,
      uid: String,
      kind: String,
      isRequired: Boolean,
      onChange: String => Msg
  ) =
    div(`class` := "form-input")(
      label(`for` := name, `class` := "form-label")(
        if (isRequired) span("*") else span(),
        text(name)
      ),
      input(`type` := kind, `class` := "form-control", id := uid, onInput(onChange))
    )

  // Util
  def setErrorStatus(message: String): Page =
    this.copy(status = Some(Page.Status(message, Page.StatusKind.ERROR)))

  def setSuccessStatus(message: String): Page =
    this.copy(status = Some(Page.Status(message, Page.StatusKind.SUCCESS)))
}

object LoginPage {
  trait Msg                                   extends App.Msg
  case class UpdateEmail(email: String)       extends Msg
  case class UpdatePassword(password: String) extends Msg
  case object AttemptLogin                    extends Msg
  case object NoOp                            extends Msg
  case class LoginError(error: String)        extends Msg
  case class LoginSuccess(token: String)      extends Msg

  object Endpoints {
    val login = new Endpoint[Msg] {
      override val location: String = Constants.endpoints.login
      override val method: Method   = Method.Post
      override val onError: HttpError => Msg =
        e => LoginError(e.toString())
      override val onSuccess: Response => Msg = response => {
        val maybeToken = response.headers.get("authorization")
        maybeToken match
          case Some(token) => LoginSuccess(token)
          case None        => LoginError("Invalid username or password")
      }
    }
  }

  object Commands {
    def login(loginInfo: LoginInfo) =
      Endpoints.login.call(loginInfo)
  }
}
