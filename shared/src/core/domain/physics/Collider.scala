package core.domain.physics

import core.domain.game.Entity

trait Collider {
  def handleCollision(entity: Entity):Unit
}
