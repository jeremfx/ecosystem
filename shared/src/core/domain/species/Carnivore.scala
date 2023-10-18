package core.domain.species

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Angle, Collider, Force, Movable, SimpleMovable, Square, Vec}
import core.domain.species.herbivores.WanderingHerbivore

import scala.util.Random

class Carnivore(id: Int, entityRepo: EntityRepository, startingPos: Vec) extends Entity(id), Square, Movable, Collider{

  private val BRAKING = -0.15
  val MAX_STEER = 1000

  private val movable = new SimpleMovable(
    startingPos,
    Vec(0.0, 0.0),
    1,
    1.25
  )
  export movable.{pos, vel, heading, mass, addForce, move, maxVel}
  override def width: Double = 25
  override def height: Double = 25

  var hunger: Int = Random.nextInt(401)
  val MAX_HUNGER = 400
  val DEATH_HUNGER = 1000
  def isHungry: Boolean = hunger > MAX_HUNGER
  var food: Option[WanderingHerbivore] = None
  override def update(): Unit = {
    wander()
    move()

    hunger += 1
    if (hunger > DEATH_HUNGER) {
      //die()
    }
    if (isHungry) {
      //seek food
      closestFood() match {
        case Some(herbivore) => seekAndEatFood(herbivore)
        case None => wander()
      }
    } else {
      wander()
    }
    //updateReproduction()
    movable.move()
    //updateAge()
  }

  def seekAndEatFood(desiredFood: WanderingHerbivore): Unit = {
    val desiredVec = desiredFood.pos - pos
    food = Some(desiredFood)
    if (desiredVec.length < desiredFood.width + width / 2 + 2) {
        hunger = 0
        food = None
        desiredFood.die()
    } else {
      val steer: Vec = (desiredVec * movable.maxVel - vel).limit(MAX_STEER)

      movable.addForce(Force("steer", steer))
    }
  }
  private def closestFood(): Option[WanderingHerbivore] = {
    val herbivores: Seq[WanderingHerbivore] = entityRepo.herbivores()
    val candidates = herbivores.sortWith((a, b) => (a.pos - pos).length < (b.pos - pos).length)
    //println("Candidates " + candidates.toString())
    candidates.headOption
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

  override def handleCollision(entity: Entity): Unit = {
    entity match {
      case e: (WanderingHerbivore | Carnivore) =>
        val vectorPush = (e.pos - pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      case _ =>
    }
  }
}
