import mill._
import mill.scalajslib._
import mill.scalalib._

object js extends ScalaJSModule {
  def scalaVersion = "2.13.2"

  def scalaJSVersion = "1.5.1"

  override def moduleDeps = Seq(common)

  override def ivyDeps = Agg(
    ivy"org.scala-js::scalajs-dom::1.1.0",
    ivy"com.lihaoyi::scalatags::0.9.2",
    ivy"com.lihaoyi::upickle::1.2.2"
  )
}

object common extends ScalaJSModule {
  def scalaVersion = "2.13.2"
  def scalaJSVersion = "1.5.1"
  override def ivyDeps = Agg(
    ivy"com.lihaoyi::utest::0.7.9"
  )

  object test extends Tests{
    override def ivyDeps = Agg(
      ivy"com.lihaoyi::utest::0.7.9"
    )
    override def testFrameworks = Seq("utest.runner.Framework")
  }

}

object jvm extends ScalaModule {
  def scalaVersion = "2.13.2"

  override def moduleDeps = Seq(common)
}

object server extends ScalaModule {
  def scalaVersion = "2.13.2"

  override def ivyDeps = Agg(
    ivy"com.lihaoyi::cask::0.7.7",
    ivy"com.lihaoyi::scalatags::0.9.4"
  )

}
