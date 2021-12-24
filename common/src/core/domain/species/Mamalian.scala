package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Collider, Force, Movable, Positionable, TwoDimensional, Vec}

abstract class Mamalian(entityRepo: EntityRepository, startingPos: Vec) extends Entity with TwoDimensional
with Positionable with Movable with Collider {
  override def update(): Unit = ???

  override def id: Int = ???

  override def width: Double = ???

  override def height: Double = ???

  override def pos: Vec = ???

  override def vel: Vec = ???

  override def heading: Angle = ???

  override def mass: Double = ???

  override def addForce(f: Force): Unit = ???

  override def move: Unit = ???

  override def maxVel: Double = ???

  override def handleCollision(entity: Entity): Unit = ???
}
