package core.domain.event

trait GameEventDispatcher {
  def register(eventHandler: GameEventHandler) : Unit
  def dispatch(event: GameEvent) : Unit
}
