package core.domain.physics

object CollisionDetection {

  def isColliding(a: TwoDimensional with Positionable, b: TwoDimensional with Positionable): Boolean = {
    //a.pos.x < b.pos.x + b.width && a.pos.x + a.width > b.pos.x && a.pos.y < b.pos.y + b.height && a.height + a.pos.y > b.pos.y
    (a.pos - b.pos).length < a.width/2 + b.width/2
  }

}
