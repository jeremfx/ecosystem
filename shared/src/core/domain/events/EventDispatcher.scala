package core.domain.events

trait EventDispatcher {
  def dispatch(event: Event) : Unit
}
