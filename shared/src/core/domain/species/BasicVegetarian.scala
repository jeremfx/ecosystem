package core.domain.species


import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Collider, Force, Movable, Positionable, SimpleMovable, TwoDimensional, Vec}

import scala.collection.mutable
import scala.util.Random

class BasicVegetarian(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity(id) with TwoDimensional
  with Positionable with Movable with Collider{

  private val BRAKING = -0.15
  val MAX_STEER = 1000

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    1.5
  )

  val REPRODUCTION_RATE = 1
  val REPRODUCTION_THRESHOLD = 1000
  var reproductionLevel = 0
  var hasReproduct = false
  private def isReproductionReady = reproductionLevel > REPRODUCTION_THRESHOLD
  private def updateReproduction = {
    if (isReproductionReady) {
      entityRepo.add(new BasicVegetarian(1, entityRepo, Vec(pos.x + width + 1, pos.y + 1)))
      reproductionLevel = 0
      hasReproduct = true
    } else {
      if (!hasReproduct) {
        reproductionLevel += REPRODUCTION_RATE
      }
    }
  }

  var hunger = Random.nextInt(801)
  val MAX_HUNGER = 600
  val DEATH_HUNGER = 1000

  private def closestFood(): Option[BasicPlant] = {
    val basicPlants: Seq[BasicPlant] = entityRepo.entities().collect({ case e: BasicPlant => e })
    val candidates = basicPlants.filter(_.canEat(400)).sortWith( (a,b) => (a.pos - pos).length < (b.pos - pos).length)
    //println("Candidates " + candidates.toString())
    candidates.headOption
  }

  override def update(): Unit = {
    hunger += 1
    if(hunger > DEATH_HUNGER){
      entityRepo.remove(this)
      entityRepo.add(new Carrion(1, entityRepo, pos, this))
    }
    if(isHungry){
      movable.maxVel = 2
      //seek food
      closestFood() match {
        case Some(basicPlant) => seekAndEatFood(basicPlant)
        case None => wander()
      }
    } else {
      movable.maxVel = 1
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

    def seekAndEatFood(desiredFood: BasicPlant): Unit = {
      val desiredVec = desiredFood.pos - pos
      if(desiredVec.length < desiredFood.width/2 + width/2 + 2){
        if (desiredFood.eat(400)) {
          hunger = 0
        }
      } else {
        val steer: Vec = (desiredVec * movable.maxVel - vel).limit(MAX_STEER)

        movable.addForce(Force("steer", steer))
      }
    }
  }

  override def handleCollision(entity: Entity): Unit = {
    entity match {
      case e: BasicVegetarian => {
        if(Random.nextInt(100) == 1){
          entityRepo.add(new BasicVegetarian(1, entityRepo, Vec(pos.x + width + 1, pos.y + 1)))
        }
        val vectorPush = ((e.pos - pos).normalize) * 2
        e.addForce(Force("push", vectorPush))
      }
      case _ =>
    }
  }

  override def width: Double = 20

  override def height: Double = 20

  override def pos: Vec = movable.pos

  override def vel: Vec = movable.vel

  override def heading: Angle = movable.heading

  override def mass: Double = movable.mass

  override def addForce(f: Force): Unit = movable.addForce(f)

  override def move(): Unit = movable.move()

  override def maxVel: Double = movable.maxVel

  def isHungry: Boolean = hunger > MAX_HUNGER

  override def toString = s"BasicVegetarian($BRAKING, $MAX_STEER, $movable, $hunger, $MAX_HUNGER)"

}
