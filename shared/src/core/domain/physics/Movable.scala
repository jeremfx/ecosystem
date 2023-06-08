package core.domain.physics

trait Movable {
  def vel: Vec
  def heading: Angle
  def mass: Double
  def addForce(f: Force): Unit
  def move(): Unit
  def maxVel: Double
}
