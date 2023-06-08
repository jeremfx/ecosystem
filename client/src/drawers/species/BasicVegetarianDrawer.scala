package drawers.species

import core.domain.species.BasicVegetarian
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object BasicVegetarianDrawer extends EntityDrawer[BasicVegetarian] {
  override def draw(ctx: CanvasRenderingContext2D, entity: BasicVegetarian, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.rect(entity.pos.x - entity.width/2 , entity.pos.y - entity.width/2, entity.width, entity.height)
/*    ctx.fillStyle = "#41A534"
    ctx.fill()
    ctx.beginPath
    ctx.arc(entity.pos.x, entity.pos.y, entity.width/2, 0, 2*Math.PI)*/
    if (entity.isHungry) {
      ctx.fillStyle = "#0081B0"

    } else {
      ctx.fillStyle = "#00B9FC"
    }
    ctx.fill()
  }
}
