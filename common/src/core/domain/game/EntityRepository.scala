package core.domain.game

import scala.collection.mutable

trait EntityRepository {
  def add(entity: Entity): Unit
  def entities(): mutable.Seq[Entity]
  def remove(id: Int): Unit
}