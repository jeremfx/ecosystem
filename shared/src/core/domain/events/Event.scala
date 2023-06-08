package core.domain.events

import core.domain.game.Entity

sealed trait Event

case class EntityRemoved(entity: Entity) extends Event
