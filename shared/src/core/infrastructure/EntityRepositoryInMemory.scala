package core.infrastructure

import core.domain.game.{Entity, EntityRepository}
import core.domain.species.Carrion

import scala.collection.mutable

class EntityRepositoryInMemory extends EntityRepository{
  private val mutableEntities = new mutable.ListBuffer[Entity]()
  private val carrionsEntities = new mutable.ListBuffer[Carrion]()

  override def add(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities += carrion
      case _ =>
    }
    mutableEntities += entity
  }

  override def entities(): Seq[Entity] = mutableEntities.toSeq

  override def remove(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities -= carrion
      case _ =>
    }
    mutableEntities -= entity
  }

  override def exists(entity: Entity): Boolean = mutableEntities.contains(entity)

  override def carrions(): Seq[Carrion] = carrionsEntities.toSeq

}
