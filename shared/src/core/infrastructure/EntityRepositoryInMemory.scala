package core.infrastructure

import core.domain.game.{Entity, EntityRepository}
import core.domain.physics.TwoDimensional
import core.domain.species.{BasicPlant, Carrion, Grass}

import scala.collection.mutable

class EntityRepositoryInMemory extends EntityRepository{
  private val mutableEntities = new mutable.ListBuffer[Entity]()
  private val carrionsEntities = new mutable.ListBuffer[Carrion]()
  private val grassEntities = new mutable.ListBuffer[Grass]()
  private val plantEntities = new mutable.ListBuffer[BasicPlant]()
  private val twoDimensionalEntities = new mutable.ListBuffer[TwoDimensional]()

  override def add(entity: Entity): Unit = {
    entity match {
      case carrion: Carrion => carrionsEntities += carrion
      case grass: Grass => grassEntities += grass
      case plant: BasicPlant => plantEntities += plant
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
      case plant: BasicPlant => plantEntities -= plant
      case twoD: TwoDimensional => twoDimensionalEntities -= twoD
      case _ =>
    }
    mutableEntities -= entity
  }

  override def exists(entity: Entity): Boolean = mutableEntities.contains(entity)

  override def carrions(): Seq[Carrion] = carrionsEntities.toSeq

  override def grass(): Seq[Grass] = grassEntities.toSeq

  override def plants(): Seq[BasicPlant] = plantEntities.toSeq
  override def twoDimensionals(): Seq[TwoDimensional] = twoDimensionalEntities.toSeq

}
