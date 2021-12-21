package core.domain.event

trait GameEventHandler {
  def handle(event: GameEvent)
}
