package core.domain.physics

case class Area(value: Double){
  def -(other: Area): Area = Area(other.value - value)
  def +(other: Area): Area = Area(other.value + value)
  def >(other: Area): Boolean = other.value > value
}
