package core.domain.species.plants

import core.domain.game.Entity
import core.domain.physics.{Area, Circle, Collider, Vec}
import core.domain.species.Edible

class BasicPlant(id: Int,  startingPos: Vec) extends Entity(id)
  with Circle(15) with Edible with Collider {
  override def update(): Unit = {}

  override def eat(chunk: Area): Boolean = {true}

  override def pos: Vec = startingPos
}
