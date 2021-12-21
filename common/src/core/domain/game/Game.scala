package core.domain.game

import core.domain.event.{GameEventDispatcher, GameEventHandler}

trait Game extends GameEventDispatcher with GameEventHandler {
  def update(): Unit

  def isTerminated: Boolean

  def entities: Seq[Entity]

  def bounds: Bounds
}
