package core.domain.species

import core.domain.game.Entity
import core.domain.physics.{Collider, Movable, Positionable, TwoDimensional, Vec}

class Grass(id: Int, startingPos: Vec) extends Entity(id), TwoDimensional, Positionable{

  val MAX_HEIGHT: Int = 5

  var currentHeight:Double = 1

  override def update(): Unit = {
    if(height<MAX_HEIGHT) {
      currentHeight += 0.01
    }
  }

  override def contains(pos: Vec): Boolean = {
    (pos - this.pos).length < width && (pos - this.pos).length < MAX_HEIGHT
  }

  override def width: Double = 2

  override def height: Double = currentHeight

  override def pos: Vec = startingPos
}
