package info.antoniojimenez.jobsboard

import tyrian.*
import tyrian.Html.*
import cats.effect.*
import scala.scalajs.js.annotation.*
import org.scalajs.dom.window
import scala.concurrent.duration.*
import tyrian.cmds.Logger

import core.*
import components.*
import info.antoniojimenez.jobsboard.pages.*

object App {
  type Msg = Router.Msg | Page.Msg

  case class Model(router: Router, page: Page)
}

@JSExportTopLevel("antonioApp")
class App extends TyrianApp[App.Msg, App.Model] { // [Message, model = "state"]
  import App.*
  /*
  We can send messages by
    - trigger a command
    - create a subscription
    - listening for an event
   */
  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) = {
    val location            = window.location.pathname
    val page                = Page.get(location)
    val pageCmd             = page.initCmd
    val (router, routerCmd) = Router.startAt(location)
    (Model(router, page), routerCmd |+| pageCmd)
  }

  // potentially endless stream of messages
  override def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.make( // listener for browser history changes
      "urlChange",
      model.router.history.state.discrete
        .map(_.get)
        .map(newLocation => Router.ChangeLocation(newLocation, true))
    )

  // model can change by receiving messages
  // model => messaage => (new model, _)
  // update triggered whenever we get a new message
  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case msg: Router.Msg =>
      val (newRouter, routerCmd) = model.router.update(msg)
      if (model.router == newRouter)  // no change is necessary
        (model, Cmd.None)
      else {
        // location changed need to re-render the appropriate page
        val newPage = Page.get(newRouter.location)
        val newPageCmd = newPage.initCmd
        (model.copy(router = newRouter, page = newPage), routerCmd |+| newPageCmd)
      }
    case msg: Page.Msg =>
      val (newPage, cmd) = model.page.update(msg)
      (model.copy(page = newPage), cmd)

  // view triggered whenever model changes
  override def view(model: Model): Html[Msg] =
    div(
      // <a href="/jobs">Jobs</a>
      Header.view(),
      model.page.view()
    )

}
