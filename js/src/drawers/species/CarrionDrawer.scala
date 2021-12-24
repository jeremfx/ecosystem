package drawers.species

import core.domain.species.Carrion
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object CarrionDrawer extends EntityDrawer[Carrion]{
  override def draw(ctx: CanvasRenderingContext2D, entity: Carrion, remainingLag: Double): Unit = {
    ctx.beginPath
    ctx.rect(entity.pos.x - entity.width/2 , entity.pos.y - entity.width/2, entity.width, entity.height)
    /*    ctx.fillStyle = "#41A534"
        ctx.fill()
        ctx.beginPath
        ctx.arc(entity.pos.x, entity.pos.y, entity.width/2, 0, 2*Math.PI)*/
      ctx.fillStyle = "#323232"

    ctx.fill()
  }
}
