package info.antoniojimenez.jobsboard

import scala.scalajs.js.annotation.* 
import org.scalajs.dom.document

@JSExportTopLevel("antonioApp")
class App {
  @JSExport
  def doSomething(containerId: String) = 
    document.getElementById(containerId).innerHTML = "THIS ROCKS"

  //
  
}
