package core.domain.species

import core.domain.physics.Area

case class Energy(value: Double) {
  def *(area: Area): Energy = Energy(area.value * this.value)
  def -(other: Energy): Energy = Energy(other.value - this.value)
}
