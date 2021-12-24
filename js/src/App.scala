import core.domain.game.Ecosystem
import drawers.SimpleGameDrawer
import org.scalajs.dom
import org.scalajs.dom.ext.Color.d
import org.scalajs.dom.html
import runner.BasicRunner

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("App")
object App {


  @JSExport
  def start(): Unit = {
    //canvas: html.Canvas, game: Game, gameDrawer: CanvasGameDrawer
    val gameRunner = new BasicRunner(dom.document.getElementById("gameCanvas").asInstanceOf[html.Canvas],
      new Ecosystem(), SimpleGameDrawer)
    gameRunner.run()
  }

}
