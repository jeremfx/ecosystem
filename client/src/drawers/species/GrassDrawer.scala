package drawers.species

import core.domain.species.Grass
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object GrassDrawer extends EntityDrawer[Grass]{
  override def draw(ctx: CanvasRenderingContext2D, entity: Grass, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.rect(entity.pos.x - entity.width / 2, entity.pos.y - entity.width / 2, entity.width, entity.height)
    ctx.fillStyle = "#62B452"
    ctx.fill()
  }
}
