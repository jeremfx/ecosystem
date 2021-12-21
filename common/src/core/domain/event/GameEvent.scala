package core.domain.event

import core.domain.game.Entity

sealed trait GameEvent

case class EntityRemoved(entity: Entity) extends GameEvent
