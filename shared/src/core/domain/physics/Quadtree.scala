package core.domain.physics

import core.domain.physics.Positionable

sealed trait Quadtree {
  def insert(obj: TwoDimensional): Quadtree
  def query(bounds: Boundaries): List[TwoDimensional]
}

case class Node(bounds: Boundaries,
                values: List[TwoDimensional] = List(),
                var topLeft: Option[Quadtree] = None,
                var topRight: Option[Quadtree] = None,
                var bottomLeft: Option[Quadtree] = None,
                var bottomRight: Option[Quadtree] = None) extends Quadtree {

  val capacity = 4 // or whatever threshold you choose

  def insert(obj: TwoDimensional): Quadtree = {
    if (!bounds.contains(obj)) return this // the object is out of this quadrant's bounds

    if (values.size < capacity && topLeft.isEmpty) {
      return this.copy(values = obj :: values)
    } else {
      // Subdivide if necessary
      if (topLeft.isEmpty) subdivide()

      // Insert into the appropriate child quadrant
      topLeft.get.insert(obj)
      topRight.get.insert(obj)
      bottomLeft.get.insert(obj)
      bottomRight.get.insert(obj)
    }

    this
  }

  def subdivide(): Unit = {
    val centerX = (bounds.xMin + bounds.xMax) / 2
    val centerY = (bounds.yMin + bounds.yMax) / 2

    topLeft = Some(Node(Boundaries(bounds.xMin, centerX, bounds.yMin, centerY)))
    topRight = Some(Node(Boundaries(centerX, bounds.xMax, bounds.yMin, centerY)))
    bottomLeft = Some(Node(Boundaries(bounds.xMin, centerX, centerY, bounds.yMax)))
    bottomRight = Some(Node(Boundaries(centerX, bounds.xMax, centerY, bounds.yMax)))
  }

  def query(searchBounds: Boundaries): List[TwoDimensional] = {
    if (!bounds.intersects(searchBounds)) return List()

    var found = values.filter(obj => searchBounds.contains(obj.pos))

    if (topLeft.nonEmpty) {
      found = found ++ topLeft.get.query(searchBounds)
      found = found ++ topRight.get.query(searchBounds)
      found = found ++ bottomLeft.get.query(searchBounds)
      found = found ++ bottomRight.get.query(searchBounds)
    }

    found
  }
}
