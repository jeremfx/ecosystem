package core.infrastructure

import core.domain.physics.Vec
import core.domain.species.BasicVegetarian
import utest.{TestSuite, Tests, test}

object EntityRepositoryInMemoryTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val repo = new EntityRepositoryInMemory
      val entity = new BasicVegetarian(1, repo, Vec(0,0))
      repo.add(entity)
      assert(repo.entities().head eq entity)
      repo.remove(entity)
      assert(repo.entities().isEmpty)
    }
  }
}
