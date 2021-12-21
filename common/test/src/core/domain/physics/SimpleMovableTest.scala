package core.domain.physics

import utest.{TestSuite, Tests, test}

object SimpleMovableTest extends TestSuite {
  val tests: Tests = Tests {
    test - {
      val movable = new SimpleMovable(Vec(5, 5), Vec(1, 1))
      test - {
        movable.addForce(Force("acceleration", Vec(1, 1)))
        movable.move()
        assert(movable.vel == Vec(2, 2))
        assert(movable.pos == Vec(7, 7))
      }
    }
  }
}
