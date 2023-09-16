package info.antoniojimenez.jobsboard.pages

import tyrian.*
import cats.effect.*
import tyrian.Html.*
import tyrian.http.*
import tyrian.cmds.Logger
import io.circe.syntax.*
import io.circe.parser.*
import io.circe.generic.auto.*

import info.antoniojimenez.jobsboard.common.*
import info.antoniojimenez.jobsboard.domain.auth.*

import info.antoniojimenez.jobsboard.*

final case class SignUpPage(
    email: String = "",
    password: String = "",
    confirmPassword: String = "",
    firstName: String = "",
    lastName: String = "",
    company: String = "",
    status: Option[Page.Status] = None
) extends Page {
  import SignUpPage.*
  def initCmd: Cmd[IO, App.Msg] =
    Cmd.None

  def update(msg: App.Msg): (Page, Cmd[IO, App.Msg]) = msg match {
    case UpdateEmail(email)       => (this.copy(email = email), Cmd.None)
    case UpdatePassword(password) => (this.copy(password = password), Cmd.None)
    case UpdateConfirmPassword(confirmPassword) =>
      (this.copy(confirmPassword = confirmPassword), Cmd.None)
    case UpdateFirstName(firstName) => (this.copy(firstName = firstName), Cmd.None)
    case UpdateLastName(lastName)   => (this.copy(lastName = lastName), Cmd.None)
    case UpdateCompany(company)     => (this.copy(company = company), Cmd.None)
    case AttemptSignUp => {
      if (!email.matches(Constants.emailRegex))
        (setErrorStatus("Email is invalid"), Cmd.None)
      else if (password.isEmpty())
        (setErrorStatus("Pleasae enter a password"), Cmd.None)
      else if (password != confirmPassword)
        (setErrorStatus("Password fields do not match"), Cmd.None)
      else
        (
          this,
          Commands.signup(
            NewUserInfo(
              email,
              password,
              Option(firstName).filter(_.nonEmpty),
              Option(lastName).filter(_.nonEmpty),
              Option(company).filter(_.nonEmpty)
            )
          )
        )
    }
    case SignUpError(message)   => (setErrorStatus(message), Cmd.None)
    case SignUpSuccess(message) => (setSuccessStatus(message), Cmd.None)
    case _                      => (this, Cmd.None)
  }

  def view(): Html[App.Msg] =
    div(`class` := "form-section")(
      div(`class` := "top-section")(
        h1("Sign Up")
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
        renderInput(
          "Confirm password",
          "confirmpassword",
          "password",
          true,
          UpdateConfirmPassword(_)
        ),
        renderInput("FirstName", "firstName", "text", false, UpdateFirstName(_)),
        renderInput("LastName", "lastName", "text", false, UpdateLastName(_)),
        renderInput("Company", "company", "text", false, UpdateCompany(_)),
        // button
        button(`type` := "button", onClick(AttemptSignUp))("Sign up")
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

object SignUpPage {
  trait Msg                                                 extends App.Msg
  case class UpdateEmail(email: String)                     extends Msg
  case class UpdatePassword(password: String)               extends Msg
  case class UpdateConfirmPassword(confirmPassword: String) extends Msg
  case class UpdateFirstName(firstName: String)             extends Msg
  case class UpdateLastName(lastName: String)               extends Msg
  case class UpdateCompany(company: String)                 extends Msg
  // actions
  case object AttemptSignUp extends Msg
  case object NoOp         extends Msg
  // statuses
  case class SignUpError(message: String)   extends Msg
  case class SignUpSuccess(message: String) extends Msg

  object Endpoints {
    val signup = new Endpoint[Msg] {
      val location = Constants.Endpoints.signup
      val method   = Method.Post
      val onSuccess: Response => Msg = response =>
        response.status match {
          case Status(201, _) =>
            SignUpSuccess("Sucess! Log in now.")
          case Status(s, _) if s >= 400 && s < 500 =>
            val json   = response.body
            val parsed = parse(json).flatMap(json => json.hcursor.get[String]("error"))
            parsed match {
              case Left(e)  => SignUpError(s"Error ${e.getMessage}")
              case Right(e) => SignUpError(e)
            }
        }

      val onError: HttpError => Msg =
        e => SignUpError(e.toString)

    }
  }

  object Commands {
    def signup(newUserInfo: NewUserInfo): Cmd[IO, Msg] = {
      Endpoints.signup.call(newUserInfo)
    }
  }
}
