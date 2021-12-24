package core.infrastructure

import core.domain.game.{Entity, EntityRepository}

import scala.collection.mutable

class EntityRepositoryInMemory extends EntityRepository{
  private val mutableEntities = new mutable.ListBuffer[Entity]()
  override def add(entity: Entity): Unit = mutableEntities += entity

  override def entities(): Seq[Entity] = mutableEntities.toSeq

  override def remove(entity: Entity): Unit = mutableEntities -= entity
}
