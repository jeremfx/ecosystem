package core.domain.physics

import utest.{TestSuite, Tests, test}

object SquareTest extends TestSuite{
  val tests: Tests = Tests {
    test - {
      val square = new Square:
        override def width: Double = 12

        override def height: Double = 12

        override def pos: Vec = Vec(7,8)
      test - {
        //square own center
        assert(square.contains(square.pos))
        //bot right corner
        assert(square.contains(Vec(13.0, 14.0)))
        //top left corner
        assert(square.contains(Vec(1.0, 2.0)))
        //x too far
        assert(!square.contains(Vec(14.0, 2.0)))
        //y too far
        assert(!square.contains(Vec(1.0, 15.0)))


      }
    }
  }
}

