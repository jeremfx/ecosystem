package core.domain.physics

import scala.collection.mutable.ListBuffer
import scala.util.Random

final class SimpleMovable(var pos: Vec, var vel: Vec, var mass: Double = 1, var maxVel: Double = Double.MaxValue)
  extends Movable
  with Positionable {

  def heading: Angle = {
    vel.normalize.toAngle
  }
  private val forces: ListBuffer[Force] = new ListBuffer[Force]
  def addForce(f: Force): Unit = forces += f

  //2 ème loi de Newton : Sommes des forces = Masse x Accéleration
  private def acceleration: Vec = {
    //println(forces)
   val acc =  sumForces.vector / mass

    acc
  }

  def externalAcceleration: Vec = {
    val acc =  sumExternalForces.vector / mass
    acc
  }

  def move(): Unit = {
    //println("Vel : " + vel)*/
    //println("Acc: "  +acceleration)
    val newVel = (vel + acceleration).limit(maxVel) + externalAcceleration
    forces.clear()
    //println("new Vel : " + newVel)
    //println("Pos : " + pos)
    val newPos = pos + newVel
    //println("Pos : " + newPos)
    vel = newVel
    pos = newPos
  }

  private def randomPositiveVel(): Vec = {
    Vec(Random.nextDouble() * 0.4 - 0.2, Random.nextFloat() * 0.4 - 0.2)
  }

  private def sumForces: Force = forces.filter(f => f.name.contains("steer")).fold(Force("Accumulated forces", Vec(0, 0)))(
      (f1: Force, f2: Force) => Force(f1.name, f1.vector + f2.vector))

  private def sumExternalForces: Force = forces.filter(f => f.name.contains("push")).fold(Force("Accumulated forces", Vec(0, 0)))(
    (f1: Force, f2: Force) => Force(f1.name, f1.vector + f2.vector))

  override def toString = s"SimpleMovable($pos, $vel, $mass, $maxVel)"
}
