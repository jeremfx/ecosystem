package core.domain.events

import scala.collection.mutable

class EventHandlerRegisterAndDispatcher extends EventHandlerRegister with EventDispatcher {
  private val listeners = new mutable.HashSet[EventHandler]()

  override def register(listener: EventHandler): Unit = listeners += listener

  override def dispatch(event: Event): Unit = listeners.foreach(listener => listener.handle(event))
}
