package info.antoniojimenez.jobsboard.components

import tyrian.*
import tyrian.Html.*
import info.antoniojimenez.jobsboard.core.*
import info.antoniojimenez.jobsboard.pages.*
import scala.scalajs.js
import scala.scalajs.js.annotation.*
import info.antoniojimenez.jobsboard.*

object Header {

  // public API

  def view() =
    div(`class` := "header-container")(
      renderLogo(),
      div(`class` := "header-nav")(
        ul(`class` := "header-links")(
          renderNavLinks()
        )
      )
    )

  // private API
  @js.native
  @JSImport("/static/img/logo.png", JSImport.Default)
  private val logoImage: String = js.native

  private def renderLogo() =
    a(
      href := "/",
      onEvent(
        "click",
        e => {
          e.preventDefault() // prevent relaoding the page
          Router.ChangeLocation("/")
        }
      )
    )(
      img(
        `class` := "home-logo",
        src     := logoImage,
        alt     := "Antonio Jimenez"
      )
    )

  private def renderNavLinks(): List[Html[App.Msg]] = {
    val constantLinks = List(
      renderSimpleNaveLink("Jobs", Page.Urls.JOBS)
    )

    val unauthedLinks = List(
      renderSimpleNaveLink("Login", Page.Urls.LOGIN),
      renderSimpleNaveLink("Sign Up", Page.Urls.SIGNUP)
    )

    val authedLinks = List(
      renderNavLink("Log Out", Page.Urls.HASH)(_ => Session.Logout)
    )

    constantLinks ++ (
      if (Session.isActive) authedLinks else unauthedLinks
    )
  }
  
  private def renderSimpleNaveLink(text: String, location: String) = 
    renderNavLink(text, location)(Router.ChangeLocation(_))

  private def renderNavLink(text: String, location: String)(location2msg: String => App.Msg) =
    li(`class` := "nav-item")(
      a(
        href    := location,
        `class` := "nav-link",
        onEvent(
          "click",
          e => {
            e.preventDefault() // prevent relaoding the page
            location2msg(location)
          }
        )
      )(text)
    )
}
