package core.domain.game

import core.domain.physics.{Collider, TwoDimensional}
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.InvasivePlant
import core.domain.species.{Carrion, Grass}

import scala.collection.mutable


trait EntityRepository {
  def add(entity: Entity): Unit
  def entities(): mutable.ListBuffer[Entity]
  def remove(entity: Entity): Unit
  def exists(entity: Entity): Boolean
  def carrions(): Seq[Carrion]
  def grass(): Seq[Grass]
  def plants(): Seq[InvasivePlant]
  def physicalEntities(): Seq[Entity with TwoDimensional with Collider]
  def herbivores(): Seq[WanderingHerbivore]

}