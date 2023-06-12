package core.domain.game

import core.domain.physics.TwoDimensional
import core.domain.species.{BasicPlant, Carrion, Grass}


trait EntityRepository {
  def add(entity: Entity): Unit
  def entities(): Seq[Entity]
  def remove(entity: Entity): Unit
  def exists(entity: Entity): Boolean
  def carrions(): Seq[Carrion]
  def grass(): Seq[Grass]
  def plants(): Seq[BasicPlant]
  def twoDimensionals(): Seq[TwoDimensional]
  
}