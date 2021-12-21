package core.domain.game

trait Entity {
  def update(): Unit
  def id: Int
}
