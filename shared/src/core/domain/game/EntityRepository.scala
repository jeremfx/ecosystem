package core.domain.game

import core.domain.physics.TwoDimensional
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.InvasivePlant
import core.domain.species.{Carrion, Grass}


trait EntityRepository {
  def add(entity: Entity): Unit
  def entities(): Seq[Entity]
  def remove(entity: Entity): Unit
  def exists(entity: Entity): Boolean
  def carrions(): Seq[Carrion]
  def grass(): Seq[Grass]
  def plants(): Seq[InvasivePlant]
  def twoDimensionals(): Seq[TwoDimensional]
  def herbivores(): Seq[WanderingHerbivore]

}