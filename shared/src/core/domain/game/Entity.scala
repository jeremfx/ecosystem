package core.domain.game

import core.domain.physics.Positionable

trait Entity(id: Int){
  def update(): Unit
}
