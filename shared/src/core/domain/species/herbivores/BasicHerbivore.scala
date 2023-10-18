package core.domain.species.herbivores

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics._
class BasicHerbivore(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity(id), Square, Movable, Collider{

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    1.5
  )

  export movable.{pos, vel, heading, mass, addForce, move, maxVel}

  def handleCollision(entity: core.domain.game.Entity): Unit = {}

  def update(): Unit = {}

  def width: Double = 20

  def height: Double = 20
}
