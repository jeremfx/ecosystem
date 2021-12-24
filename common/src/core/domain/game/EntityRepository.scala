package core.domain.game

import scala.collection.mutable

trait EntityRepository {
  def add(entity: Entity): Unit
  def entities(): Seq[Entity]
  def remove(entity: Entity): Unit
}