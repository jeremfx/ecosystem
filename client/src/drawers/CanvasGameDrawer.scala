package drawers

import core.domain.game.Game
import org.scalajs.dom.CanvasRenderingContext2D

trait CanvasGameDrawer {
  def draw(game: Game, ctx: CanvasRenderingContext2D, lag: Double): Unit
}
