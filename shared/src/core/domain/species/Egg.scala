package core.domain.species

import core.domain.game.Entity
import core.domain.physics.{Positionable, TwoDimensional, Vec}

trait Egg[Oviparous] extends Entity with TwoDimensional with Positionable {
}
