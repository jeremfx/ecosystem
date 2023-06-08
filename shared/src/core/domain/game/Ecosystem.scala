package core.domain.game

import core.domain.commands.{BasicCommandHandler, Command}
import core.domain.events.EventHandler
import core.domain.physics._
import core.domain.species.{BasicPlant, BasicVegetarian, Carrion, Insect}
import core.infrastructure.EntityRepositoryInMemory

import scala.util.Random

class Ecosystem extends Game {

  private val entityRepo = new EntityRepositoryInMemory
  private val commandHandler = new BasicCommandHandler(entityRepo)
  private var ticks = 0
  private var isPaused = false

  createRandomPlants(3).foreach(entityRepo.add(_))
  createRandomVegetarian(10).foreach(entityRepo.add(_))
  createRandomInsects(100).foreach(entityRepo.add(_))
  createRandomCarrion(5).foreach(entityRepo.add(_))

  def createRandomVegetarian(numberOfVegetarian: Int): Seq[BasicVegetarian] = {
    Range(0, numberOfVegetarian).map(i => new BasicVegetarian(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
  }

  def createRandomPlants(numberOfPlants: Int): Seq[BasicPlant] = {
    Range(0, numberOfPlants).map(i => new BasicPlant(i, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height)), Random.nextInt(1601) + 400))
  }

  def createRandomInsects(numberOfInsects: Int): Seq[Insect] = {
    Range(0, numberOfInsects).map(i => new Insect(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
  }

  def createRandomCarrion(numberOfCarrions: Int): Seq[Carrion] = {
    Range(0, numberOfCarrions).map(i => new Carrion(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height)), new TwoDimensional {
      override def width: Double = 50
      override def height: Double = 50
    } ))
  }

  override def bounds: Bounds = Bounds(1280, 720)

  override def update(): Unit = {
    if(isPaused) return

    entityRepo.entities().foreach(_.update())
    val entitiesWithCollider = entityRepo.entities().collect({ case i: TwoDimensional with Positionable with Collider => i })
    entitiesWithCollider.foreach(i => {
      entitiesWithCollider.foreach(j => {
        if (i != j && CollisionDetection.isColliding(i, j))
          i match {
            case e: Collider => e.handleCollision(j)
          }
      })
    })
    entityRepo.entities().collect({ case m: Movable => {
      if(m.vel.length > 0){
        val reverseHeading = m.heading + Angle(Math.PI)
        val friction = reverseHeading.toVec.normalize * Math.min(0.1, m.vel.length)
        m.addForce(Force("push", friction))
      }
    } })

    ticks = ticks + 1
  }

  override def isTerminated: Boolean = false

  override def entities: Seq[Entity] = entityRepo.entities()

  override def register(eventHandler: EventHandler): Unit = {}

  override def handle(command: Command): Unit = commandHandler.handle(command)

  override def pause(): Unit = isPaused = !isPaused
}