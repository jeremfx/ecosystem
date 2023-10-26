package core.domain.physics

import scala.language.postfixOps

trait Circle(var radius: Double) extends TwoDimensional {
  override def contains(point: Vec): Boolean = {
    (point - this.pos).length <= radius
  }
  def area():Area = Area(Math.PI * (radius * radius))

  override def width:Double = radius *2
  override def height: Double = radius * 2
  override def intersects(other: TwoDimensional): Boolean = {
    other match {
      case circle: Circle => circle.intersects(this)
      case default => super.intersects(default)
    }
  }
  def intersects(circle: Circle): Boolean = {
    (pos - circle.pos).length < radius + circle.radius
  }
}
object Circle {
  def area(radius: Double):Area = Area(Math.PI * (radius * radius))
  def radius(area: Area):Length = Length(Math.sqrt(area.value/Math.PI))
}
