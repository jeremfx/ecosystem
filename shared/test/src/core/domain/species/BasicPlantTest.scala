package core.domain.species

import core.domain.physics.{Area, Vec}
import core.infrastructure.EntityRepositoryInMemory
import utest.{TestSuite, Tests, test}

import scala.util.Random

object BasicPlantTest extends TestSuite {
  val tests: Tests = Tests {
    test("plant init") {
      val entityRepo = new EntityRepositoryInMemory
      val plant1 = new BasicPlant(1, entityRepo, Vec(100, 100), 5)
      val plant2 = new BasicPlant(1, entityRepo, Vec(125, 100), 5)
      entityRepo.add(plant1)
      entityRepo.add(plant2)
      /*      test("eat") - {
        val beforeArea = plant.area()
        plant.eat(Area(5))
        val afterArea = plant.area()
        Math.sqrt(82.1238486/Math.PI)
        assert(afterArea == (beforeArea - Area(5)))
      }*/
      test("grow") {
        plant1.update()
        plant1.update()
        assert(plant1.radius == 5.2)
      }
    }
  }
}