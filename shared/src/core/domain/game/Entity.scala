package core.domain.game

import core.domain.physics.Positionable

trait Entity(id: Int) extends Positionable {
  def update(): Unit
}
