package drawers
import core.domain.game.{Bounds, Game, PerlinNoise}
import org.scalajs.dom.CanvasRenderingContext2D

object SimpleGameDrawer extends CanvasGameDrawer {

  override def draw(game: Game, ctx: CanvasRenderingContext2D, lag: Double): Unit = {
    ctx.clearRect(0, 0, game.bounds.width, game.bounds.height)
    drawBoundaries(game, ctx)
    game.entities.foreach(GenericEntityDrawer.draw(ctx,_, lag))

  }

  def drawBoundaries(game:Game, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    ctx.strokeStyle = "grey"
    ctx.beginPath()
    ctx.moveTo(0,0)
    ctx.lineTo(game.bounds.width, 0)
    ctx.lineTo(game.bounds.width, game.bounds.height)
    ctx.lineTo(0, game.bounds.height)
    ctx.lineTo(0, 0)
    ctx.stroke()
    ctx.closePath()
    ctx.restore()

/*    val perlinNoise = PerlinNoise.generatePerlinNoise(game.bounds, 0.004)

    for (i <- perlinNoise.indices) {
      for (j <- perlinNoise(i).indices) {
        val color = perlinNoise(i)(j) * 255
        ctx.fillStyle = "rgb(" + color + ", " + color + ", " + color + ")"
        ctx.fillRect(i, j, 1, 1)
      }
    }*/

  }
}
