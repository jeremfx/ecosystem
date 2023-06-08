package core.domain.physics


case class Vec(x: Double, y: Double) {
  def +(other: Vec): Vec = Vec(x + other.x, y + other.y)

  def -(other: Vec): Vec = Vec(x - other.x, y - other.y)

  def %(other: Vec): Vec = Vec(x % other.x, y % other.y)

  def <(other: Vec): Boolean = x < other.x && y < other.y

  def >(other: Vec): Boolean = x > other.x && y > other.y

  def /(value: Double): Vec = Vec(x / value, y / value)

  def *(value: Double): Vec = Vec(x * value, y * value)

  def *(other: Vec): Double = x * other.x + y * other.y

  lazy val length: Double = Math.sqrt(lengthSquared)

  def lengthSquared: Double = x * x + y * y

  def within(a: Vec, b: Vec, extra: Vec = Vec(0, 0)): Boolean = {
    import math.{max, min}
    x >= min(a.x, b.x) - extra.x &&
      x < max(a.x, b.x) + extra.y &&
      y >= min(a.y, b.y) - extra.x &&
      y < max(a.y, b.y) + extra.y
  }

/*  def rotate(theta: Double): Vec = {
    val (cos, sin) = (Math.cos(theta), math.sin(theta))
    Vec(cos * x - sin * y, sin * x + cos * y)
  }*/

  def toAngle: Angle = {
    // On inverse y car dans le canvas l'axe y est inversé
    val angle = Angle(Math.atan2(-y,x))
    // atan2() retourne un resultat compris entre PI et -PI,
    // si le resultat est négatif on ajoute 2PI pour avoir un resultat entre 0 et 2PI
    if(angle.radians>0) angle else angle + Angle(2*Math.PI)
  }

  def normalize: Vec = if(length == 0) Vec(0,0) else Vec(x / length, y / length)

  def limit(limit: Double): Vec = {
    if(this.length>limit){
      this.normalize*limit
    } else {
      this.copy()
    }
  }
}



