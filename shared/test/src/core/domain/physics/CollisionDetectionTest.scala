package core.domain.physics

import core.domain.species.{BasicPlant, Herbivore}
import core.infrastructure.EntityRepositoryInMemory
import utest.{TestSuite, Tests, test}

object CollisionDetectionTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val vg = new Herbivore(1, new EntityRepositoryInMemory, Vec(80,80))
      val plant = new BasicPlant(1, new EntityRepositoryInMemory, Vec(90,90), 100)

      val length = (Vec(80,80) - Vec(90,90)).length
      assert(length < 5 + 10)
    }
  }
}