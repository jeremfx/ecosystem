package drawers.species

import core.domain.species.{Egg, Insect}
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object InsectEggDrawer extends EntityDrawer[Egg]{
  override def draw(ctx: CanvasRenderingContext2D, entity: Egg, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.rect(entity.pos.x - entity.width / 2, entity.pos.y - entity.width / 2, entity.width, entity.height)
    ctx.fillStyle = "#323232"
    ctx.fill()
  }
}
