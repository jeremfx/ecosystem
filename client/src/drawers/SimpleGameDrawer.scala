package drawers
import core.domain.game.{Bounds, Game}
import org.scalajs.dom.CanvasRenderingContext2D

object SimpleGameDrawer extends CanvasGameDrawer {

  override def draw(game: Game, ctx: CanvasRenderingContext2D, lag: Double): Unit = {
    ctx.clearRect(0, 0, game.bounds.width, game.bounds.height)
    drawBoundaries(game, ctx)
    game.entities.foreach(GenericEntityDrawer.draw(ctx,_, lag))
  }

  def drawBoundaries(game:Game, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    ctx.strokeStyle = "blue"
    ctx.beginPath()
    ctx.moveTo(0,0)
    ctx.lineTo(game.bounds.width, 0)
    ctx.lineTo(game.bounds.width, game.bounds.height)
    ctx.lineTo(0, game.bounds.height)
    ctx.lineTo(0, 0)
    ctx.stroke()
    ctx.closePath()
    ctx.restore()
  }
}
