package core.domain.physics

case class Boundaries(xMin: Double, xMax: Double, yMin: Double, yMax: Double) {
  def contains(point: Vec): Boolean =
    point.x >= xMin && point.x <= xMax && point.y >= yMin && point.y <= yMax

  def contains(obj: TwoDimensional): Boolean =
    contains(Vec(obj.pos.x - obj.width/2, obj.pos.y - obj.height/2)) &&
      contains(Vec(obj.pos.x + obj.width/2, obj.pos.y + obj.height/2))

  def intersects(bounds: Boundaries): Boolean =
    xMin <= bounds.xMax && xMax >= bounds.xMin && yMin <= bounds.yMax && yMax >= bounds.yMin
}