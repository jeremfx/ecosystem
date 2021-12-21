package core.domain.species


import core.domain.game.Entity
import core.domain.physics.{Collider, Force, Positionable, TwoDimensional, Vec}

class BasicPlant(id: Int, startingPos: Vec) extends Entity with TwoDimensional with Positionable with Edible with Collider {

  private val GROWTH_RATE: Double = 2.6
  var size: Double = 200
  val MAX_SIZE: Double = 1000
  val MIN_SIZE: Double = 200

  override def update(): Unit = {
    if (size <= MAX_SIZE) {
      size += GROWTH_RATE
      if(size > MAX_SIZE) size = MAX_SIZE
    }
  }

  override def id(): Int = id

  override def width: Double = Math.sqrt(size)

  override def height: Double = Math.sqrt(size)

  override def pos: Vec = startingPos


  override def eat(chunk: Double): Boolean = {
    if(size > chunk + MIN_SIZE) {
      size = size - chunk
      true
    } else {
      false
    }
  }

  def isMaxSize = size >= MAX_SIZE

  override def toString = s"BasicPlant($GROWTH_RATE, $size, $MAX_SIZE, $MIN_SIZE)"

  override def handleCollision(entity: Entity): Unit = {
    entity match {
      case e: BasicVegetarian => {
        println("colliding")
        val vectorPush = (e.pos - e.pos).normalize * 10
        e.addForce(Force("push", vectorPush))
      }
      case _ =>
    }
  }

  def canEat(chunkSize: Int): Boolean = {
    size - chunkSize > MIN_SIZE
  }

}
