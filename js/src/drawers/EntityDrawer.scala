package drawers

import core.domain.game.Entity
import org.scalajs.dom

trait EntityDrawer[T <: Entity] {
  def draw(ctx: dom.CanvasRenderingContext2D, entity: T, remainingLag: Double)
}
