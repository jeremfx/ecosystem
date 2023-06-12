package core.domain.species

import core.domain.physics.Area

trait Edible {
  def eat(chunk: Area): Boolean
}
