package drawers.species

import core.domain.species.BasicPlant
import drawers.EntityDrawer
import org.scalajs.dom.CanvasRenderingContext2D

object BasicPlantDrawer extends EntityDrawer[BasicPlant] {
  override def draw(ctx: CanvasRenderingContext2D, entity: BasicPlant, remainingLag: Double): Unit = {
    ctx.beginPath()
    ctx.arc(entity.pos.x, entity.pos.y, entity.radius, 0, 2*Math.PI)
    if(entity.isMaxRadius){
      ctx.fillStyle = "#389319"
    } else {
      ctx.fillStyle = "#41A534"
    }
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
