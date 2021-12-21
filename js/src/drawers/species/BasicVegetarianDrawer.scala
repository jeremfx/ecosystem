package drawers.species

import core.domain.species.{BasicPlant, BasicVegetarian}
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object BasicVegetarianDrawer extends EntityDrawer[BasicVegetarian] {
  override def draw(ctx: CanvasRenderingContext2D, entity: BasicVegetarian, remainingLag: Double): Unit = {
    ctx.beginPath
    ctx.rect(entity.pos.x - entity.width/2 , entity.pos.y - entity.height/2, entity.width, entity.height)
    if(entity.isHungry){
      ctx.fillStyle = "#0081B0"

    }else {
      ctx.fillStyle = "#00B9FC"
    }
    ctx.fill()
  }
}
