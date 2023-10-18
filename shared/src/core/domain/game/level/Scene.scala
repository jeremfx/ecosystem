package core.domain.game.level

import core.domain.game.{Bounds, EntityRepository}

trait Scene {
  def init(entityRepo: EntityRepository, bounds: Bounds): Unit
}
