package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Area, Collider, Force, Positionable, TwoDimensional, Vec}

class Carrion(id: Int, entityRepo: EntityRepository, startingPos: Vec, dimension: TwoDimensional) extends Entity(id) with TwoDimensional
  with Positionable with Edible with Collider {

  var decayLevel: Double = 0
  val DECAY_THRESHOLD: Int = 10*3000
  var size: Double = (dimension.width-5) * (dimension.height-5)
  private def isDecayed = decayLevel >= DECAY_THRESHOLD

  override def update(): Unit = {
    if(isDecayed) {
      entityRepo.remove(this)
    } else {
      decayLevel += 0.75
    }
  }


  override def width: Double = Math.sqrt(size)

  override def height: Double = Math.sqrt(size)

  override def pos: Vec = startingPos

  override def eat(chunk: Area): Boolean = {
    size = size - chunk.value
    if(size <= 0) entityRepo.remove(this)
    true
  }

  override def handleCollision(entity: Entity): Unit = {
    entity match {
      case e: BasicVegetarian => {
        val vectorPush = (e.pos - pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      }
      case _ =>
    }
  }
}
