package core.domain.events

trait EventHandlerRegister {
  def register(eventHandler: EventHandler) : Unit
}
