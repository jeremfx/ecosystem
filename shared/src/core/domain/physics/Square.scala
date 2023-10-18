package core.domain.physics

trait Square extends TwoDimensional {
  override def contains(point: Vec): Boolean = {
    point.x >= (pos.x - width / 2) &&
      point.x <= (pos.x + width / 2) &&
      point.y >= (pos.y - height / 2) &&
      point.y <= (pos.y + height / 2)
  }
}
