package core.domain.species.plants

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics._
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.{Edible, Carnivore}

import scala.util.Random

class InvasivePlant(id: Int, entityRepository: EntityRepository, startingPos: Vec, startingRadius: Int) extends Entity(id)
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
    if (radius < InvasivePlant.MAX_RADIUS) {
      radius += RADIUS_GROWTH_RATE
      if(entityRepository.plants().exists(twoD => !twoD.equals(this) && twoD.intersects(this))){
        radius -= RADIUS_GROWTH_RATE
      }
      if (radius > InvasivePlant.MAX_RADIUS) radius = InvasivePlant.MAX_RADIUS
    }
  }

  private def replicate(): Unit = {
    if (isMaxRadius) {
      if(isReplicationReady()){
        val unitRandomVec = Angle(Random.nextDouble() * 2 * Math.PI).toVec
        val spawnPoint: Vec = unitRandomVec * Random.between(3,InvasivePlant.MAX_RADIUS * 2 + 10) + pos
        val newPlant = new InvasivePlant(1, entityRepository, spawnPoint, InvasivePlant.MIN_RADIUS)
        if (!entityRepository.physicalEntities().exists(e => e.intersects(newPlant))) {
          entityRepository.add(newPlant)
        }
        replication = 0
      }
      replication += 1
    }
  }

  def isMaxRadius: Boolean = radius >= InvasivePlant.MAX_RADIUS

  override def eat(chunk: Area): Boolean = {
    if (canEat(chunk)) {
      radius = Circle.radius(area() - chunk).value
      true
    } else {
      false
    }
  }

  def canEat(chunkSize: Area): Boolean = {
    area() - chunkSize > InvasivePlant.MIN_AREA()
  }

  override def pos: Vec = startingPos
}

object InvasivePlant {
  val MAX_RADIUS: Int = 15
  val MIN_RADIUS: Int = 5

  private def MIN_AREA(): Area = Circle.area(MIN_RADIUS)
}