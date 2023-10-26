package core.domain.physics

import core.domain.game.Entity
import core.domain.species.plants.InvasivePlant

object CollisionDetection {

  def isColliding(a: TwoDimensional, b: TwoDimensional): Boolean = {
    //a.pos.x < b.pos.x + b.width && a.pos.x + a.width > b.pos.x && a.pos.y < b.pos.y + b.height && a.height + a.pos.y > b.pos.y
    if(a.isInstanceOf[InvasivePlant] && b.isInstanceOf[InvasivePlant]){
      false
    } else {
      (a.pos - b.pos).length < a.width/2 + b.width/2
    }
  }

  def handleCollision(a: Collider with Positionable, b: Collider with Positionable): Unit = {
    a match {
      case e: Movable with Positionable =>
        val vectorPush = (e.pos - b.pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      case _ =>
    }
    b match {
      case e: Movable with Positionable =>
        val vectorPush = (e.pos - a.pos).normalize * 2
        e.addForce(Force("push", vectorPush))
      case _ =>
    }
  }

}
