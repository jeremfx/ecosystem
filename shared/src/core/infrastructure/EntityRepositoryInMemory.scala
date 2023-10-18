package core.infrastructure

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.TwoDimensional
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
  private val twoDimensionalEntities = new mutable.ListBuffer[TwoDimensional]()

  override def add(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities += carrion
      case grass: Grass => grassEntities += grass
      case plant: InvasivePlant => plantEntities += plant
      case herbivore: WanderingHerbivore => herbivoresEntities += herbivore
      case _ =>
    }
    entity match {
      case twoDimensional: TwoDimensional => twoDimensionalEntities += twoDimensional
    }
    mutableEntities += entity
  }

  override def entities(): Seq[Entity] = mutableEntities.toSeq

  override def remove(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities -= carrion
      case grass: Grass => grassEntities -= grass
      case plant: InvasivePlant => plantEntities -= plant
      case herbivore: WanderingHerbivore => herbivoresEntities -= herbivore
      case _ =>
    }

    entity match {
      case twoDimensional: TwoDimensional => twoDimensionalEntities -= twoDimensional
    }
    
    mutableEntities -= entity
  }

  override def exists(entity: Entity): Boolean = mutableEntities.contains(entity)

  override def carrions(): Seq[Carrion] = carrionsEntities.toSeq

  override def grass(): Seq[Grass] = grassEntities.toSeq

  override def plants(): Seq[InvasivePlant] = plantEntities.toSeq
  override def twoDimensionals(): Seq[TwoDimensional] = twoDimensionalEntities.toSeq
  override def herbivores(): Seq[WanderingHerbivore] = herbivoresEntities.toSeq

}
