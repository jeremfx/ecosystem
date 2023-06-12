package core.domain.species


import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.*

import scala.util.Random

class BasicPlant(id: Int, entityRepository: EntityRepository, startingPos: Vec, startingRadius: Int) extends Entity(id)
  with Circle(startingRadius) with Edible with Collider {

  private val RADIUS_GROWTH_RATE: Double = 0.1
  private val REPLICATION_BUILD_TIME: Int = 2*30
  private var replication: Int = Random.nextInt(REPLICATION_BUILD_TIME)

  private def isReplicationReady(): Boolean = replication >= REPLICATION_BUILD_TIME
  override def update(): Unit = {
    grow()
    replicate()
  }

  private def grow(): Unit = {
    if (radius < BasicPlant.MAX_RADIUS) {
      radius += RADIUS_GROWTH_RATE
      if(entityRepository.twoDimensionals().exists(twoD => !twoD.equals(this) && twoD.intersects(this))){
        radius -= RADIUS_GROWTH_RATE
      }
      if (radius > BasicPlant.MAX_RADIUS) radius = BasicPlant.MAX_RADIUS
    }
  }

  private def replicate(): Unit = {
    if (isMaxRadius) {
      if(isReplicationReady()){
        val unitRandomVec = Angle(Random.nextDouble() * 2 * Math.PI).toVec
        val spawnPoint: Vec = unitRandomVec * Random.between(3,BasicPlant.MAX_RADIUS * 2 + 10) + pos
        val newPlant = new BasicPlant(1, entityRepository, spawnPoint, BasicPlant.MIN_RADIUS)
        if (!entityRepository.twoDimensionals().exists(e => e.intersects(newPlant))) {
          entityRepository.add(newPlant)
        }
        replication = 0
      }
      replication += 1
    }
  }

  def isMaxRadius: Boolean = radius >= BasicPlant.MAX_RADIUS

  override def eat(chunk: Area): Boolean = {
    if (canEat(chunk)) {
      radius = Circle.radius(area() - chunk).value
      true
    } else {
      false
    }
  }

  override def handleCollision(entity: Entity): Unit =
    entity match {
      case e: (Herbivore | Carnivore) => {
        val vectorPush = (e.pos - pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      }
/*      case e: BasicPlant => {
        var thisToOther = e.pos - pos
        if (thisToOther.length == 0) {
          thisToOther = Vec(Random.between(-0.1, 0.11), Random.between(-0.1, 0.11))
        }
        val vectorPush = thisToOther.normalize * 0.12
        e.addForce(Force("push", vectorPush))
      }*/
      case _ =>
    }

  def canEat(chunkSize: Area): Boolean = {
    area() - chunkSize > BasicPlant.MIN_AREA()
  }

  override def pos: Vec = startingPos
}

object BasicPlant {
  val MAX_RADIUS: Int = 15
  val MIN_RADIUS: Int = 5

  private def MIN_AREA(): Area = Circle.area(MIN_RADIUS)
}