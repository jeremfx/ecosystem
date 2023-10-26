package core.domain.game.level
import core.domain.game.{Bounds, EntityRepository, PerlinNoise}
import core.domain.physics.Vec
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.InvasivePlant
import core.domain.species.{Carnivore, Grass, Insect}

import scala.collection.mutable
import scala.util.Random

object ComplexScene extends Scene{
  override def init(entityRepo: EntityRepository, bounds: Bounds): Unit = {
    val perlinNoiseByPixel: Array[Array[Double]] = PerlinNoise.generatePerlinNoise(bounds, 0.004)
    //createRandomGrass(perlinNoiseByPixel).foreach(entityRepo.add(_))
    createRandomPlants(perlinNoiseByPixel).foreach(entityRepo.add(_))
    //createRandomPlants(5).foreach(entityRepo.add(_))
    createRandomHerbivores(80).foreach(entityRepo.add(_))
    createRandomCarnivores(8).foreach(entityRepo.add(_))
    createRandomInsects(15).foreach(entityRepo.add(_))
    //createRandomCarrion(5).foreach(entityRepo.add(_))

    def createRandomHerbivores(numberOfVegetarian: Int): Seq[WanderingHerbivore] = {
      Range(0, numberOfVegetarian).map(i => new WanderingHerbivore(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
    }

    def createRandomCarnivores(number: Int): Seq[Carnivore] = {
      Range(0, number).map(i => new Carnivore(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height))))
    }

    def createRandomPlants(perlinNoise: Array[Array[Double]]): Seq[InvasivePlant] = {
      val plants = new mutable.ListBuffer[InvasivePlant]()
      var nextPlantSize = nextRandomPlantSize()
      for (i <- perlinNoise.indices) {
        for (j <- perlinNoise(i).indices) {
          val newPlant = new InvasivePlant(1, entityRepo, Vec(i, j), nextPlantSize)
          if (perlinNoise(i)(j) > 0.67 && !plants.exists(e => e.intersects(newPlant))) {
            plants += newPlant
            nextPlantSize = nextRandomPlantSize()
          }
        }
      }
      plants.toSeq
    }

    def nextRandomPlantSize(): Int = {
      //Guarantees some max radius plant (wich can reproduce)
      if (Random.nextInt(5) > 3) {
        InvasivePlant.MAX_RADIUS
      } else {
        Random.between(InvasivePlant.MIN_RADIUS, InvasivePlant.MAX_RADIUS)
      }
    }

/*    def createRandomPlants(numberOfPlants: Int): Seq[BasicPlant] = {
      Range(0, numberOfPlants).map(i => new BasicPlant(i, entityRepo, Vec(Random.nextInt(bounds.width), Random.nextInt(bounds.height)),
        Random.nextInt(BasicPlant.MAX_RADIUS)))
    }*/

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
  }
}
