package drawers.species

import core.domain.species.BasicPlant
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object BasicPlantDrawer extends EntityDrawer[BasicPlant] {
  override def draw(ctx: CanvasRenderingContext2D, entity: BasicPlant, remainingLag: Double): Unit = {
    ctx.beginPath
    val width = entity.width
    val height = entity.height
    ctx.arc(entity.pos.x - width/2 , entity.pos.y - height/2, width, 0, 2*Math.PI)
    if(entity.isMaxSize){
      ctx.fillStyle = "#41A534"
    } else
      ctx.fillStyle = "#62B452"
    ctx.fill()
  }

  private def lengthToPixel(length: Double) = {
    val w = Math.floor(length).toInt
    if (w % 2 == 0) {
      w
    } else {
      w - 1
    }
  }
}
