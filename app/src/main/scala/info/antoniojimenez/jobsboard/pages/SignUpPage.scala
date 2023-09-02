package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import cats.effect.*

final case class SignUpPage() extends Page {
  def initCmd: Cmd[IO, Page.Msg] = 
    Cmd.None

  def update(msg: Page.Msg): (Page, Cmd[IO, Page.Msg]) = 
    (this, Cmd.None)

  def view(): Html[Page.Msg] =
    div("Sign up page - TODO")
}
