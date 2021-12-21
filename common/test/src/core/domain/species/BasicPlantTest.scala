package core.domain.species

import core.domain.physics.Vec
import utest.{TestSuite, Tests, test}

object BasicPlantTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val plant = new BasicPlant(1, Vec(0,0))
      test - {
        plant.eat(5)
        assert(plant.size == 395)
      }
      test - {
        while(!plant.isMaxSize){
          plant.update()
        }
        plant.eat(5)
        assert(plant.size == 795)
        plant.update()
        assert(plant.size == 797.6)
        assert(!plant.isMaxSize)
        plant.update()
        assert(plant.size > 797.6)
        assert(plant.isMaxSize)
      }
    }
  }
}
