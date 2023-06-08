package core.domain.game

trait Entity(id: Int) {
  def update(): Unit
}
