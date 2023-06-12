package drawers.species

import core.domain.species.{Carnivore, Herbivore}
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object CarnivoreDrawer extends EntityDrawer[Carnivore] {
  override def draw(ctx: CanvasRenderingContext2D, entity: Carnivore, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.moveTo(entity.pos.x - entity.width/2, entity.pos.y + entity.height/2)
    ctx.lineTo(entity.pos.x, entity.pos.y - entity.height/2 +3)
    ctx.lineTo(entity.pos.x + entity.width/2, entity.pos.y + entity.height/2)
    ctx.lineTo(entity.pos.x - entity.width/2, entity.pos.y + entity.height/2)
    ctx.closePath()
    ctx.fillStyle = "#ff3333"
    ctx.fill()
    entity.food match {
      case Some(f) => {
        ctx.beginPath()
        ctx.moveTo(entity.pos.x, entity.pos.y)
        ctx.lineTo(f.pos.x, f.pos.y)
        ctx.strokeStyle = "#bfbfbf"
        ctx.stroke()
        ctx.closePath()
      }
      case None =>
    }
  }
}
