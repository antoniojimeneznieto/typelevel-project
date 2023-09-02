package info.antoniojimenez.jobsboard.playground

import tyrian.*
import tyrian.Html.*
import cats.effect.*
import scala.scalajs.js.annotation.*
import org.scalajs.dom.{document, console}
import scala.concurrent.duration.*
import tyrian.cmds.Logger

object PlaygroundApp {
  sealed trait Msg
  case class Increment(amount: Int) extends Msg

  case class Model(count: Int)
}
// @JSExportTopLevel("antonioApp")
class PlaygroundApp extends TyrianApp[PlaygroundApp.Msg, PlaygroundApp.Model] { // [Message, model = "state"]
  import PlaygroundApp.*
  /*
  We can send messages by
    - trigger a command
    - create a subscription
    - listening for an event
   */
  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (Model(0), Cmd.None)

  // potentially endless stream of messages
  override def subscriptions(model: Model): Sub[IO, Msg] =
    // Sub.None
    Sub.every[IO](1.second).map(_ => Increment(1))

  // model can change by receiving messages
  // model => messaage => (new model, _)
  // update triggered whenever we get a new message
  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Increment(amount) => {
      (
        model.copy(count = model.count + amount),
        Logger.consoleLog[IO]("Changing count by " + amount)
      )
    }
  // view triggered whenever model changes
  override def view(model: Model): Html[Msg] =
    div(
      button(onClick(Increment(1)))("increase"),
      button(onClick(Increment(-1)))("decrease"),
      div(s"Tyrian running: ${model.count}")
    )

}
