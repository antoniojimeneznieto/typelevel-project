package info.antoniojimenez.jobsboard.components

import tyrian.*
import tyrian.Html.*
import info.antoniojimenez.jobsboard.core.*
import info.antoniojimenez.jobsboard.pages.*
import scala.scalajs.js
import scala.scalajs.js.annotation.*

object Header {

  // public API

  def view() =
    div(`class` := "header-container")(
      renderLogo(),
      div(`class` := "header-nav")(
        ul(`class` := "header-links")(
          renderNavLink("Jobs", Page.Urls.JOBS),
          renderNavLink("Login", Page.Urls.LOGIN),
          renderNavLink("Sign up", Page.Urls.SIGNUP)
        )
      )
    )

  // private API
  @js.native
  @JSImport("/static/img/logo.png", JSImport.Default)
  private val logoImage: String = js.native

  private def renderLogo() =
    a(
        href    := "/",
        onEvent(
          "click",
          e => {
            e.preventDefault() // prevent relaoding the page
            Router.ChangeLocation("/")
          }
        )
      )(img(
        `class` := "home-logo",
        src := logoImage,
        alt := "Antonio Jimenez"
      ))

  private def renderNavLink(text: String, location: String) =
    li(`class` := "nav-item")(
      a(
        href    := location,
        `class` := "nav-link",
        onEvent(
          "click",
          e => {
            e.preventDefault() // prevent relaoding the page
            Router.ChangeLocation(location)
          }
        )
      )(text)
    )
}
