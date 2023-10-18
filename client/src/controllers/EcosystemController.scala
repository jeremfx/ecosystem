package controllers

import core.domain.commands.{CreateBasicPlant, RemoveBasicPlant}
import core.domain.game.Game
import core.domain.physics.Vec
import events.DomEventHandler
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.{KeyboardEvent, MouseEvent}

class EcosystemController(ecosystem: Game, canvas: Canvas) extends DomEventHandler {
  override def onMouseDown(e: MouseEvent): Unit = {}

  override def onMouseUp(e: MouseEvent): Unit = {}

  override def onClick(e: MouseEvent): Unit = {
    ecosystem.pause()
  }

  override def onKeyDown(e: KeyboardEvent): Unit = {}

  override def onKeyPress(e: KeyboardEvent): Unit = {
    if(e.key == "p"){
      ecosystem.pause()
    }
  }

  override def onKeyUp(e: KeyboardEvent): Unit = {}

  private def getPositionInCanvas(x: Double, y: Double): Vec = {
    val rect = canvas.getBoundingClientRect();
    Vec(x - rect.left, y - rect.top)
  }

  override def onDoubleClick(e: MouseEvent): Unit = {
    val eventPos = getPositionInCanvas(e.clientX, e.clientY)
    if (e.button == 0) {
      ecosystem.handle(CreateBasicPlant(eventPos))
    } else if (e.button == 2) {
      ecosystem.handle(RemoveBasicPlant(eventPos))
    }
  }
}
