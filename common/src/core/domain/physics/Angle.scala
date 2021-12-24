package core.domain.physics

case class Angle(radians: Double) {
  def -(other: Angle) : Angle = {
    Angle(radians - other.radians)
  }

  def +(other: Angle) : Angle = {
    Angle(radians + other.radians)
  }

  def %(other: Angle) : Angle = {
    Angle(radians % other.radians)
  }

  def * (value: Double) = Angle(radians*value)

  def degrees: Double = Math.toDegrees(radians)

  // On inverse y car dans le canvas l'axe y est inversé
  def toVec: Vec = Vec(Math.cos(radians), -Math.sin(radians))
}