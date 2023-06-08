package runner

import controllers.EcosystemController
import core.domain.game.Game
import drawers.CanvasGameDrawer
import events.DomEventDispatcher
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.{Date, timers}

class BasicRunner(canvas: html.Canvas, game: Game, gameDrawer: CanvasGameDrawer) extends GameRunner {

  val ctx: dom.CanvasRenderingContext2D = initCanvasCtx()
  private val perfStats = new PerfStats()
  private val updateTimeStep = 32
  private var lastUpdateTs: Double = getCurrentTimeInMs
  initEventHandling()
  private var isAnimationFrameRequested: Boolean = false
  DomEventDispatcher.register(new EcosystemController(game, canvas))

  def initEventHandling(): Unit = {
    canvas.onmousedown = e => {
      DomEventDispatcher.onMouseDown(e)
    }
    canvas.onmouseup = e => {
      DomEventDispatcher.onMouseUp(e)
    }
    canvas.onclick = e => {
      DomEventDispatcher.onClick(e)
    }
    canvas.onkeydown = e => {
      DomEventDispatcher.onKeyDown(e)
    }
    canvas.onkeypress = e => {
      DomEventDispatcher.onKeyPress(e)
    }
    canvas.onkeyup = e => {
      DomEventDispatcher.onKeyUp(e)
    }
  }

  def run(): Unit = {
    println("RUN")
    val drawInterval = timers.setInterval(4) {
      if (!isAnimationFrameRequested) {
        dom.window.requestAnimationFrame((ts: Double) => draw())
        isAnimationFrameRequested = true
      }
      update()
    }
  }

  // Limitation du navigateur 60 fps. dom.window.requestAnimationFrame nous call maximum 60 fois par seconde
  private def draw(): Unit = {
    val current = getCurrentTimeInMs
    isAnimationFrameRequested = false
    if (!game.isTerminated) {
      gameDrawer.draw(game, ctx, (current - lastUpdateTs) / updateTimeStep)
      perfStats.logDraw(current)
      perfStats.drawStats(getCurrentTimeInMs, ctx)
    }
  }

  private def getCurrentTimeInMs: Double = new Date().getTime()

  def update(): Unit = {
    if (!game.isTerminated) {
      val current = getCurrentTimeInMs
      val elapsed = current - lastUpdateTs
      var lag = elapsed
      while (lag >= updateTimeStep && !game.isTerminated) {
        lastUpdateTs = getCurrentTimeInMs
        perfStats.logUpdate(lastUpdateTs)
        perfStats.logNumberOfEntites(game.entities.length)
        game.update()
        lag -= updateTimeStep
      }
    }
  }

  def initCanvasCtx(): dom.CanvasRenderingContext2D = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.width = game.bounds.width
    canvas.height = game.bounds.height
    ctx
  }

  /*  override def handle(event: Event): Unit = {
  /*    event match {
          case e: EntityRemoved => e.entity match {
          case s: BasicShip => DomEventDispatcher.unregister(spaceShipController)
          case _ =>
        }
        case _ =>
      }*/
    }*/

}
