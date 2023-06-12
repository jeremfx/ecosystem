package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Area, Collider, Force, Movable, Positionable, SimpleMovable, TwoDimensional, Vec}

import scala.util.Random

class Insect(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity(id), TwoDimensional,
  Positionable, Movable, Oviparous {

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    2
  )
  export movable.{pos, vel, heading, mass, addForce, move, maxVel}

  val MAX_STEER: Double = 1000
  var hunger: Int = Random.nextInt(601)
  val MAX_HUNGER = 600
  val DEATH_HUNGER = 1000
  def isHungry: Boolean = hunger > MAX_HUNGER

  var closestFood: Option[Carrion] = Option.empty

  private def updateClosestFood(): Unit = {
    if(!closestFood.exists(c => entityRepo.carrions().contains(c))){
      closestFood = seekClosestFood()
    }
  }

  private def seekClosestFood(): Option[Carrion] = {
    entityRepo.carrions().minByOption(carrion => (carrion.pos - pos).lengthSquared)
  }

  private val EGG_BUILDING_RATE = 1
  private val EGG_THRESHOLD = 500
  private var eggBuildingLevel = 0
  private def isEggReady = eggBuildingLevel > EGG_THRESHOLD
  private def updateReproduction(): Unit = {
    if(isEggReady){
      layEgg()
      print("layEgg")
      eggBuildingLevel = 0
    } else {
      eggBuildingLevel += EGG_BUILDING_RATE
    }
  }

  override def update(): Unit = {
    updateHungerStatus()
    if(isHungry){
      movable.maxVel = 4
      seekFood()
    } else {
      movable.maxVel = 2
      wander()
    }
    updateReproduction()
    movable.move()
  }

  private def updateHungerStatus(): Unit = {
    hunger += 1
    if (hunger > DEATH_HUNGER) {
      entityRepo.remove(this)
    }
  }

  private def seekFood(): Unit = {
    updateClosestFood()
    closestFood match {
      case Some(carrion) => seekAndEatFood(carrion)
      case None => wander()
    }
  }

  def seekAndEatFood(desiredFood: Carrion): Unit = {
    val desiredVec = desiredFood.pos - pos
    if (desiredVec.length < desiredFood.width / 2 + width / 2 + 2) {
      if (desiredFood.eat(Area(1.0))) {
        hunger -= 5
      }
    } else {
      val steer: Vec = (desiredVec * movable.maxVel - vel).limit(MAX_STEER)

      movable.addForce(Force("steer", steer))
    }
  }

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

  override def width: Double = 4
  override def height: Double = 4
  def layEgg(): Unit = {
    entityRepo.add(new InsectEgg(1, entityRepo, pos))
  }
}

