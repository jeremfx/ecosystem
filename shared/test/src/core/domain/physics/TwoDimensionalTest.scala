package core.domain.physics

import utest.{TestSuite, Tests, test}

object TwoDimensionalTest extends TestSuite {
  val tests: Tests = Tests {
    test("intersects") {
      val td1 = new TwoDimensional:
        override def width: Double = 10

        override def height: Double = 10

        override def pos: Vec = Vec(15, 15)

      val td2 = new TwoDimensional:
        override def width: Double = 10

        override def height: Double = 10

        override def pos: Vec = Vec(15, 15)

      assert(td1.intersects(td2))
    }
  }
}
