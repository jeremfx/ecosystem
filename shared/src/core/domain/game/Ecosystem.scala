package core.domain.game

import core.domain.commands.{BasicCommandHandler, Command}
import core.domain.events.EventHandler
import core.domain.physics.*
import core.domain.species.*
import core.infrastructure.EntityRepositoryInMemory

import scala.collection.mutable
import scala.util.Random

class Ecosystem extends Game {

  val perlinNoiseByPixel: Array[Array[Double]] = PerlinNoise.generatePerlinNoise(bounds, 0.004)
  private val entityRepo = new EntityRepositoryInMemory
  private val commandHandler = new BasicCommandHandler(entityRepo)
  private var ticks = 0
  private var isPaused = false

  //createRandomGrass(perlinNoiseByPixel).foreach(entityRepo.add(_))
  createRandomPlants(perlinNoiseByPixel).foreach(entityRepo.add(_))
  //createRandomPlants(5).foreach(entityRepo.add(_))
  createRandomHerbivores(10).foreach(entityRepo.add(_))
  createRandomCarnivores(2).foreach(entityRepo.add(_))
  createRandomInsects(15).foreach(entityRepo.add(_))
  //createRandomCarrion(5).foreach(entityRepo.add(_))

  def createRandomHerbivores(numberOfVegetarian: Int): Seq[Herbivore] = {
    Range(0, numberOfVegetarian).map(i => new Herbivore(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
  }

  def createRandomCarnivores(number: Int): Seq[Carnivore] = {
    Range(0, number).map(i => new Carnivore(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
  }

  override def bounds: Bounds = Bounds(1280, 720)

  def createRandomPlants(perlinNoise: Array[Array[Double]]): Seq[BasicPlant] = {
    val plants = new mutable.ListBuffer[BasicPlant]()
    var nextPlantSize = nextRandomPlantSize()
    for (i <- perlinNoise.indices) {
      for (j <- perlinNoise(i).indices) {
        val newPlant = new BasicPlant(1, entityRepo, Vec(i, j), nextPlantSize)
        if (perlinNoise(i)(j) > 0.67 && !plants.exists(e => e.intersects(newPlant))) {
          plants += newPlant
          nextPlantSize = nextRandomPlantSize()
        }
      }
    }
    plants.toSeq
  }

  private def nextRandomPlantSize(): Int = {
    //Guarantees some max radius plant (wich can reproduce)
    if(Random.nextInt(5) > 3){
      BasicPlant.MAX_RADIUS
    } else {
      Random.between(BasicPlant.MIN_RADIUS, BasicPlant.MAX_RADIUS)
    }
  }

  def createRandomPlants(numberOfPlants: Int): Seq[BasicPlant] = {
    Range(0, numberOfPlants).map(i => new BasicPlant(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height)),
      Random.nextInt(BasicPlant.MAX_RADIUS)))
  }

  def createRandomInsects(numberOfInsects: Int): Seq[Insect] = {
    Range(0, numberOfInsects).map(i => new Insect(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
  }
  /*  def createRandomCarrion(numberOfCarrions: Int): Seq[Carrion] = {
      Range(0, numberOfCarrions).map(i => new Carrion(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height)), new TwoDimensional {
        override def width: Double = 50
        override def height: Double = 50
      } ))
    }*/

  def createRandomGrass(perlinNoise: Array[Array[Double]]): Seq[Grass] = {
    val grass = new mutable.ListBuffer[Grass]()
    for (i <- perlinNoise.indices) {
      for (j <- perlinNoise(i).indices) {
        if (perlinNoise(i)(j) < 0.40 && !entityRepo.grass().exists(_.contains(Vec(i, j)))) {
          grass += new Grass(1, Vec(i, j))
        }
      }
    }
    grass.toSeq
  }

  override def update(): Unit = {
    if (isPaused) {return}
    entityRepo.entities().foreach(_.update())
    val entitiesWithCollider = entityRepo.entities().collect({ case i: TwoDimensional with Collider => i })
    entitiesWithCollider.foreach(i => {
      entitiesWithCollider.foreach(j => {
        if (i != j && CollisionDetection.isColliding(i, j))
          i match {
            case e: Collider => e.handleCollision(j)
          }
      })
    })
    entityRepo.entities().collect({ case m: Movable => {
      if (m.vel.length > 0) {
        val reverseHeading = m.heading + Angle(Math.PI)
        val friction = reverseHeading.toVec.normalize * Math.min(0.1, m.vel.length)
        m.addForce(Force("push", friction))
      }
    }
    })

    ticks = ticks + 1
  }

  override def isTerminated: Boolean = false

  override def entities: Seq[Entity] = entityRepo.entities()

  override def register(eventHandler: EventHandler): Unit = {}

  override def handle(command: Command): Unit = commandHandler.handle(command)

  override def pause(): Unit = isPaused = !isPaused
}
