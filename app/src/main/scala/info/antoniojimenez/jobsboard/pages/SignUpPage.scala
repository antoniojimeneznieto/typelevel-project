package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import cats.effect.*
import tyrian.cmds.Logger
import info.antoniojimenez.jobsboard.common.*

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
  def initCmd: Cmd[IO, Page.Msg] =
    Cmd.None

  def update(msg: Page.Msg): (Page, Cmd[IO, Page.Msg]) = msg match {
    case UpdateEmail(email)       => (this.copy(email = email), Cmd.None)
    case UpdatePassword(password) => (this.copy(password = password), Cmd.None)
    case UpdateConfirmPassword(confirmPassword) =>
      (this.copy(confirmPassword = confirmPassword), Cmd.None)
    case UpdateFirstName(firstName) => (this.copy(firstName = firstName), Cmd.None)
    case UpdateLastName(lastName)   => (this.copy(lastName = lastName), Cmd.None)
    case UpdateCompany(company)     => (this.copy(company = company), Cmd.None)
    case AttempSignUp => {
      if (!email.matches(Constants.emailRegex))
        (setErrorStatus("Email is invalid"), Cmd.None)
      else if (password.isEmpty())
        (setErrorStatus("Pleasae enter a password"), Cmd.None)
      else if (password != confirmPassword)
        (setErrorStatus("Password fields do not match"), Cmd.None)
      else
        (this, Logger.consoleLog[IO]("Signing up!", email, password, firstName, lastName, company))
    }
    case _ => (this, Cmd.None)
  }

  def view(): Html[Page.Msg] =
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
        button(`type` := "button", onClick(AttempSignUp))("Sign up")
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
      input(`type` := "text", `class` := "form-control", id := uid, onInput(onChange))
    )

  // Util
  def setErrorStatus(message: String): Page =
    this.copy(status = Some(Page.Status(message, Page.StatusKind.ERROR)))
}

object SignUpPage {
  trait Msg                                                 extends Page.Msg
  case class UpdateEmail(email: String)                     extends Msg
  case class UpdatePassword(password: String)               extends Msg
  case class UpdateConfirmPassword(confirmPassword: String) extends Msg
  case class UpdateFirstName(firstName: String)             extends Msg
  case class UpdateLastName(lastName: String)               extends Msg
  case class UpdateCompany(company: String)                 extends Msg
  // actions
  case object AttempSignUp extends Msg
  case object NoOp         extends Msg
}
