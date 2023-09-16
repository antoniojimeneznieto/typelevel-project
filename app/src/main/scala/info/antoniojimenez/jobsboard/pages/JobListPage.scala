package info.antoniojimenez.jobsboard.pages

import tyrian.*
import tyrian.Html.*
import cats.effect.*

import info.antoniojimenez.jobsboard.*

final case class JobListPage() extends Page {
  def initCmd: Cmd[IO, App.Msg] = 
    Cmd.None

  def update(msg: App.Msg): (Page, Cmd[IO, App.Msg]) = 
    (this, Cmd.None)

  def view(): Html[App.Msg] =
    div("Job list page - TODO")
}
