package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Collider, Force, Movable, Positionable, SimpleMovable, TwoDimensional, Vec}

import scala.util.Random

class Insect(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity with TwoDimensional
  with Positionable with Movable {

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    2
  )

  val MAX_STEER: Double = 1000
  var hunger = Random.nextInt(601)
  val MAX_HUNGER = 600
  val DEATH_HUNGER = 1000
  def isHungry: Boolean = hunger > MAX_HUNGER

  private def closestFood(): Option[Carrion] = {
    val basicPlants: Seq[Carrion] = entityRepo.entities().collect({ case e: Carrion => e })
    val candidates = basicPlants.sortWith( (a,b) => (a.pos - pos).length < (b.pos - pos).length)
    //println("Candidates " + candidates.toString())
    candidates.headOption
  }

  val REPRODUCTION_RATE = 1
  val REPRODUCTION_THRESHOLD = 500
  var reproductionLevel = 0
  var hasReproduct = false
  private def isReproductionReady = reproductionLevel > REPRODUCTION_THRESHOLD
  private def updateReproduction = {
    if(isReproductionReady){
      (1 to 2).foreach(i => entityRepo.add(new Insect(i, entityRepo, Vec(pos.x + width + 1, pos.y + 1))))
      reproductionLevel = 0
      hasReproduct = true
    } else {
      if(!hasReproduct){
        reproductionLevel += REPRODUCTION_RATE
      }
    }
  }

  override def update(): Unit = {
    hunger += 1
    if(hunger > DEATH_HUNGER){
      entityRepo.remove(this)
    }
    if(isHungry){
      //seek food
      closestFood() match {
        case Some(carrion) => seekAndEatFood(carrion)
        case None => wander()
      }
    } else {
      wander()
    }
    updateReproduction
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

    def seekAndEatFood(desiredFood: Carrion): Unit = {
      val desiredVec = desiredFood.pos - pos
      if(desiredVec.length < desiredFood.width/2 + width/2 + 2){
        if (desiredFood.eat(1)) {
          hunger -= 1
        }
      } else {
        val steer: Vec = (desiredVec * movable.maxVel - vel).limit(MAX_STEER)

        movable.addForce(Force("steer", steer))
      }
    }
  }

  override def id(): Int = id

  override def width: Double = 4

  override def height: Double = 4

  override def pos: Vec = movable.pos

  override def vel: Vec = movable.vel

  override def heading: Angle = movable.heading

  override def mass: Double = movable.mass

  override def addForce(f: Force): Unit = movable.addForce(f)

  override def move: Unit = movable.move()

  override def maxVel: Double = movable.maxVel

}

