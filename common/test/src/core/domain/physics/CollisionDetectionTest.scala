package core.domain.physics

import core.domain.species.{BasicPlant, BasicVegetarian}
import core.infrastructure.EntityRepositoryInMemory
import utest.{TestSuite, Tests, test}

object CollisionDetectionTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val vg = new BasicVegetarian(1, new EntityRepositoryInMemory, Vec(80,80))
      val plant = new BasicPlant(1, Vec(90,90), 100)

      val length = (Vec(80,80) - Vec(90,90)).length
      assert(length < 5 + 10)
    }
  }
}