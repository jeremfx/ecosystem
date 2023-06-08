package core.domain.game

import core.domain.species.Carrion


trait EntityRepository {
  def add(entity: Entity): Unit

  def entities(): Seq[Entity]

  def remove(entity: Entity): Unit

  def exists(entity: Entity): Boolean

  def carrions(): Seq[Carrion]
}