package core.domain.physics

trait TwoDimensional extends Positionable {
  def width: Double
  def height: Double
  def contains(point: Vec): Boolean = false
  def intersects(other: TwoDimensional): Boolean = {
    val isRight = pos.x + width / 2 < other.pos.x - other.width/2
    val isLeft = pos.x - width / 2 > other.pos.x + other.width/2
    val isAbove = pos.y - height/2 > other.pos.y + height/2
    val isBelow = pos.y + height/2 < other.pos.y - height/2
    !isRight && !isLeft && !isAbove && !isBelow
  }
}