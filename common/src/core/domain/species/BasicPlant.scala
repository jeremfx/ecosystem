package core.domain.species


import core.domain.game.Entity
import core.domain.physics.{Angle, Collider, Force, Movable, Positionable, SimpleMovable, TwoDimensional, Vec}

import scala.util.Random

class BasicPlant(id: Int, startingPos: Vec, startingSize: Int) extends Entity with TwoDimensional with Positionable with Edible with Collider with Movable {


  private val GROWTH_RATE: Double = 2.6
  var size: Double = startingSize
  val MAX_SIZE: Double = 2000
  val MIN_SIZE: Double = 800

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0, 0),
    1,
    3
  )


  def contains(pos: Vec): Boolean = {
    (pos - this.pos).length < width
  }

  override def update(): Unit = {
    if (size <= MAX_SIZE) {
      size += GROWTH_RATE
      if(size > MAX_SIZE) size = MAX_SIZE
    }
    movable.move()
  }

  override def id(): Int = id

  override def width: Double = Math.sqrt(size)

  override def height: Double = Math.sqrt(size)

  override def pos: Vec = movable.pos


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

  override def handleCollision(entity: Entity): Unit =
    entity match {
      case e: BasicVegetarian => {
        val vectorPush = (e.pos - pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      }
      case e: BasicPlant => {
        var thisToOther = e.pos - pos
        if(thisToOther.length == 0) {
          thisToOther = Vec(Random.between(-0.1, 0.11), Random.between(-0.1, 0.11))
        }
        val vectorPush = thisToOther.normalize * 0.12
        e.addForce(Force("push", vectorPush))
       }
      case _ =>
    }

  def canEat(chunkSize: Int): Boolean = {
    size - chunkSize > MIN_SIZE
  }

  override def vel: Vec = movable.vel

  override def heading: Angle = movable.heading

  override def mass: Double = movable.mass

  override def addForce(f: Force): Unit = movable.addForce(f)

  override def move: Unit = movable.move()

  override def maxVel: Double = movable.maxVel
}
