import mill._, scalalib._, scalajslib._

trait AppScalaModule extends ScalaModule {
  def scalaVersion = "3.3.0"
}

trait AppScalaJSModule extends AppScalaModule with ScalaJSModule {
  def scalaJSVersion = "1.13.1"
}

object server extends AppScalaModule {
  override def moduleDeps = Seq(shared.jvm)

  override def ivyDeps = Agg(
    ivy"com.lihaoyi::cask::0.9.1",
    ivy"com.lihaoyi::scalatags::0.12.0"
  )
}

object shared extends Module {
  trait SharedModule extends AppScalaModule with PlatformScalaModule {
  }

  object jvm extends SharedModule {
    object test extends ScalaTests {
      def testFramework = "utest.runner.Framework"

      override def ivyDeps = Agg(
        ivy"com.lihaoyi::utest::0.7.10",
        ivy"com.lihaoyi::requests::0.6.9",
      )
    }
  }

  object js extends SharedModule with AppScalaJSModule
}

object client extends AppScalaJSModule {
  override def moduleDeps = Seq(shared.js)

  override def ivyDeps = Agg(
    ivy"org.scala-js::scalajs-dom::2.6.0",
    ivy"com.lihaoyi::scalatags::0.12.0",
    ivy"com.lihaoyi::upickle::3.1.0"
  )
}