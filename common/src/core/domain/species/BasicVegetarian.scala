package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Force, Movable, Positionable, SimpleMovable, TwoDimensional, Vec}

import scala.collection.mutable
import scala.util.Random

class BasicVegetarian(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity with TwoDimensional with Positionable with Movable {

  val ACCELERATION = 0.8
  private val BRAKING = -0.15
  val MAX_STEER = 1000

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    1.5
  )

  var hunger = 600
  val MAX_HUNGER = 600

  private def closestFood(): Option[BasicPlant] = {
    val basicPlants: mutable.Seq[BasicPlant] = entityRepo.entities().collect({ case e: BasicPlant => e })
    val candidates = basicPlants.filter(_.canEat(400)).sortWith( (a,b) => (a.pos - pos).length < (b.pos - pos).length)
    //println("Candidates " + candidates.toString())
    candidates.headOption
  }

  override def update(): Unit = {
    hunger += 1
    if(isHungry){
      //seek food
      closestFood() match {
        case Some(basicPlant) => seekAndEatFood(basicPlant)
        case None => wander()
      }
    } else {
      wander()
    }
    movable.move()

    def wander(): Unit = {
      val centerOfCircleInFront = pos + (heading.toVec * 100)
      //println("centerOfCircleInFront = " + pos + " + (" + heading.toVec + " * " + "100)")
      val randomAngle = Random.nextDouble() * 2 * Math.PI
      val vectorFromCircle = Angle(randomAngle).toVec * 30
      val desired = (centerOfCircleInFront + vectorFromCircle) - pos
      //println("desired : " + desired + " = " + centerOfCircleInFront  + " + " + vectorFromCircle + " - " + pos)
      val steer: Vec = (desired * movable.maxVel - vel).limit(MAX_STEER)
      movable.addForce(Force("steer", steer))
    }

    def seekAndEatFood(desiredFood: BasicPlant): Unit = {
      val desiredVec = desiredFood.pos - pos
      if(desiredVec.length < desiredFood.width + width){
        if (desiredFood.eat(400)) {
          hunger = 450
        }
      } else {
        val steer: Vec = (desiredVec * movable.maxVel - vel).limit(MAX_STEER)
        movable.addForce(Force("steer", steer))
      }
    }
  }


  override def width: Double = 20

  override def height: Double = 20

  override def pos: Vec = movable.pos

  override def id(): Int = id

  override def vel: Vec = movable.vel

  override def heading: Angle = movable.heading

  override def mass: Double = movable.mass

  override def addForce(f: Force): Unit = movable.addForce(f)

  override def move: Unit = movable.move()

  override def maxVel: Double = movable.maxVel

  def isHungry: Boolean = hunger > MAX_HUNGER

  override def toString = s"BasicVegetarian($ACCELERATION, $BRAKING, $MAX_STEER, $movable, $hunger, $MAX_HUNGER)"
}
