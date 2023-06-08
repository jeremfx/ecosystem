package core.domain.species

trait Oviparous[T] {
  def layEggs(eggs: List[Egg[T]]): Boolean
}
