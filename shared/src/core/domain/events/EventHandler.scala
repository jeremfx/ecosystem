package core.domain.events

trait EventHandler {
  def handle(event: Event): Unit
}
