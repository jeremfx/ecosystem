package core.infrastructure

import core.domain.game.{Entity, EntityRepository}

import scala.collection.mutable

class EntityRepositoryInMemory extends EntityRepository{
  private val mutableEntities = new mutable.ListBuffer[Entity]()
  override def add(entity: Entity): Unit = mutableEntities += entity

  override def entities(): mutable.Seq[Entity] = mutableEntities

  override def remove(id: Int): Unit = mutableEntities.filterInPlace(e => e.id != id)
}
