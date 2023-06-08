package core.domain.species

trait Edible {
  def eat(chunk: Double): Boolean
}
