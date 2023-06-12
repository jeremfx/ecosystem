package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.Vec

import scala.util.Random

class InsectEgg(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Egg, Entity(id){

  private val HATCH_THRESHOLD = 300
  private var hatchingLevel = 0
  override def update(): Unit = {
    if(hatchingLevel < HATCH_THRESHOLD){
      hatchingLevel += 1
    } else {
      hatchingLevel = 0
      entityRepo.add(new Insect(1, entityRepo, pos))
      entityRepo.remove(this)
    }
  }

  override def width: Double = 1

  override def height: Double = 1

  override def pos: Vec = startingPos

  override def contains(point: Vec): Boolean = point.equals(pos)
}
