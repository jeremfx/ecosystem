package core.domain.game.level
import core.domain.game.{Bounds, EntityRepository}
import core.domain.physics.Vec
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.{BasicPlant, InvasivePlant}

import scala.util.Random

object HerbivoreFoodScene extends Scene {
  override def init(entityRepo: EntityRepository, bounds: Bounds): Unit = {
    entityRepo.add(new WanderingHerbivore(1, entityRepo, Vec(100,100)))
    entityRepo.add(new BasicPlant(1, Vec(400, 400)))
  }
}
