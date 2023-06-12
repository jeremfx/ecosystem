package drawers.species

import core.domain.species.Insect
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object InsectDrawer extends EntityDrawer[Insect] {
  override def draw(ctx: CanvasRenderingContext2D, entity: Insect, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.rect(entity.pos.x - entity.width/2 , entity.pos.y - entity.width/2, entity.width, entity.height)
    /*    ctx.fillStyle = "#41A534"
        ctx.fill()
        ctx.beginPath
        ctx.arc(entity.pos.x, entity.pos.y, entity.width/2, 0, 2*Math.PI)*/

      ctx.fillStyle = "#FFD662"
    ctx.fill()
  }
}
