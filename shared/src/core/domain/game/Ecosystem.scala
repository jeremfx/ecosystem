package core.domain.game

import core.domain.commands.{BasicCommandHandler, Command}
import core.domain.events.EventHandler
import core.domain.game.level.{ComplexScene, HerbivoreFoodScene}
import core.domain.physics._
import core.domain.species._
import core.infrastructure.EntityRepositoryInMemory

import scala.collection.mutable
import scala.util.Random

class Ecosystem extends Game {

  private val entityRepo = new EntityRepositoryInMemory
  private val commandHandler = new BasicCommandHandler(entityRepo)
  private var ticks = 0
  private var isPaused = false

  override def bounds: Bounds = Bounds(1900, 900)

  //HerbivoreFoodScene.init(entityRepo, bounds)
  ComplexScene.init(entityRepo, bounds);
  override def update(): Unit = {
    if (isPaused) {return}
    entities.foreach(_.update())
    handlePhysics()
/*    entityRepo.entities().collect({ case m: Movable => {
      if (m.vel.length > 0) {
        val reverseHeading = m.heading + Angle(Math.PI)
        val friction = reverseHeading.toVec.normalize * Math.min(0.1, m.vel.length)
        m.addForce(Force("push", friction))
      }
    }
    })*/

    ticks = ticks + 1
  }

  def handlePhysics() = {
    val entities = entityRepo.physicalEntities().toList
    entities.zipWithIndex.foreach { case (entity1, idx1) =>
      entities.drop(idx1 + 1).foreach { entity2 =>
        if (CollisionDetection.isColliding(entity1, entity2)) {
          CollisionDetection.handleCollision(entity1, entity2)
        }
      }
    }
  }

  override def isTerminated: Boolean = false

  override def entities: Seq[Entity] = entityRepo.entities().toSeq

  override def register(eventHandler: EventHandler): Unit = {}

  override def handle(command: Command): Unit = commandHandler.handle(command)

  override def pause(): Unit = isPaused = !isPaused
}
