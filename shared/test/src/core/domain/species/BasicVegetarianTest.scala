package core.domain.species

import core.domain.physics.{Force, SimpleMovable, Vec}
import core.infrastructure.EntityRepositoryInMemory
import utest.{TestSuite, Tests, test}


object BasicVegetarianTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val entityRepo = new EntityRepositoryInMemory
      val vegetarian = new BasicVegetarian(1, entityRepo, Vec(0,0))
      test - {
        vegetarian.update()
        vegetarian.update()
        assert(vegetarian.hunger == 2)
      }
    }
  }
}