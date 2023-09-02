package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import cats.effect.*

final case class NotFoundPage() extends Page {
  def initCmd: Cmd[IO, Page.Msg] = 
    Cmd.None

  def update(msg: Page.Msg): (Page, Cmd[IO, Page.Msg]) = 
    (this, Cmd.None)

  def view(): Html[Page.Msg] =
    div("This page doesn't exist.")
}
