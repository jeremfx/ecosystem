package core.domain.game

import core.domain.commands.CommandHandler
import core.domain.events.EventHandlerRegister

trait Game extends EventHandlerRegister with CommandHandler {
  def update(): Unit

  def isTerminated: Boolean

  def entities: Seq[Entity]

  def bounds: Bounds
}
