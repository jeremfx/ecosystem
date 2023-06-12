package core.domain.physics

import utest.{TestSuite, Tests, test}

object CircleTest extends TestSuite{
  val tests: Tests = Tests {
    test - {
      val circle = new Circle(7):
        override def pos: Vec = Vec(10.0, 12.0)
      test - {
        //circle own center
        assert(circle.contains(circle.pos))
        //inside
        assert(circle.contains(Vec(7.0, 14.0)))
        //outside by x
        assert(!circle.contains(Vec(18.0, 14.0)))
        //outside by y
        assert(!circle.contains(Vec(7.0, 20.0)))
        //outside by both
        assert(!circle.contains(Vec(1.0, 1.0)))

      }
    }
  }
}
