package core.infrastructure

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.{Collider, TwoDimensional}
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.InvasivePlant
import core.domain.species.{Carrion, Grass}

import scala.collection.mutable

class EntityRepositoryInMemory extends EntityRepository{
  private val mutableEntities = new mutable.ListBuffer[Entity]()
  private val carrionsEntities = new mutable.ListBuffer[Carrion]()
  private val grassEntities = new mutable.ListBuffer[Grass]()
  private val plantEntities = new mutable.ListBuffer[InvasivePlant]()
  private val herbivoresEntities = new mutable.ListBuffer[WanderingHerbivore]()
  private val physicalMutableEntities = new mutable.ListBuffer[Entity with TwoDimensional with Collider]()

  override def add(entity: Entity): Unit = {
    if(entity.pos)
    entity match {
      case carrion: Carrion => carrionsEntities += carrion
      case grass: Grass => grassEntities += grass
      case plant: InvasivePlant => plantEntities += plant
      case herbivore: WanderingHerbivore => herbivoresEntities += herbivore
      case _ =>
    }
    entity match {
      case physicalEntity: Entity with TwoDimensional with Collider => physicalMutableEntities += physicalEntity
      case _ =>
    }
    mutableEntities += entity
  }

  override def entities(): mutable.ListBuffer[Entity] = mutableEntities

  override def remove(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities -= carrion
      case grass: Grass => grassEntities -= grass
      case plant: InvasivePlant => plantEntities -= plant
      case herbivore: WanderingHerbivore => herbivoresEntities -= herbivore
      case _ =>
    }

    entity match {
      case physicalEntity: Entity with TwoDimensional with Collider => physicalMutableEntities -= physicalEntity
      case _ =>
    }
    
    mutableEntities -= entity
  }

  override def exists(entity: Entity): Boolean = mutableEntities.contains(entity)

  override def carrions(): Seq[Carrion] = carrionsEntities.toSeq

  override def grass(): Seq[Grass] = grassEntities.toSeq

  override def plants(): Seq[InvasivePlant] = plantEntities.toSeq
  override def physicalEntities(): Seq[Entity with TwoDimensional with Collider] = physicalMutableEntities.toSeq
  override def herbivores(): Seq[WanderingHerbivore] = herbivoresEntities.toSeq
}
