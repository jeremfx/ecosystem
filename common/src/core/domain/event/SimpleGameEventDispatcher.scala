package core.domain.event

import scala.collection.mutable

class SimpleGameEventDispatcher extends GameEventDispatcher {
  private val listeners = new mutable.HashSet[GameEventHandler]()

  override def register(listener: GameEventHandler): Unit = listeners += listener

  override def dispatch(event: GameEvent): Unit = listeners.foreach(listener => listener.handle(event))
}
