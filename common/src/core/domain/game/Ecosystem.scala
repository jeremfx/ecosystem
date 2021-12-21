package core.domain.game
import core.domain.event.{GameEvent, GameEventHandler}
import core.domain.physics.{Collider, CollisionDetection, Movable, Positionable, TwoDimensional, Vec}
import core.domain.species.{BasicPlant, BasicVegetarian}
import core.infrastructure.EntityRepositoryInMemory

import scala.collection.mutable
import scala.util.Random

class Ecosystem extends Game {

  private val entityRepo = new EntityRepositoryInMemory
  private var ticks = 0

  val basicVegetarian = new BasicVegetarian(2, entityRepo, Vec(500,500))

  createRandomPlants(40).foreach(entityRepo.add(_))
  createRandomVegetarian(10).foreach(entityRepo.add(_))

  def createRandomVegetarian(numberOfVegetarian: Int): Seq[BasicVegetarian] = {
    Range(0,numberOfVegetarian).map(i => new BasicVegetarian(4, entityRepo, Vec(Random.nextInt(bounds.width),Random.nextInt(bounds.height))))
  }

  def createRandomPlants(numberOfPlants: Int): Seq[BasicPlant] = {
    Range(0,numberOfPlants).map(i => new BasicPlant(4, Vec(Random.nextInt(bounds.width),Random.nextInt(bounds.height))))
  }

  override def update(): Unit = {
/*    if(ticks < 1) {*/
      if(ticks % 2 == 0){
      }
      entityRepo.entities().foreach(_.update())
      entityRepo.entities().collect({case i :TwoDimensional with Positionable => i }).foreach(i =>{
        entityRepo.entities().collect({case i :TwoDimensional with Positionable => i }).foreach(j => {
          if(i != j && CollisionDetection.isColliding(i,j))
            i match {
              case e:Collider => e.handleCollision(j)
              case _ =>
            }
        })
      })

      ticks = ticks + 1
/*   }*/
  }

  override def isTerminated: Boolean = {false}

  override def entities: Seq[Entity] = entityRepo.entities().toSeq

  override def bounds: Bounds = Bounds(1280, 720)

  override def handle(event: GameEvent): Unit = {}

  override def register(eventHandler: GameEventHandler): Unit = {}

  override def dispatch(event: GameEvent): Unit = {}
}
