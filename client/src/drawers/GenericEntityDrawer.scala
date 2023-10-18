package drawers

import core.domain.game.Entity
import core.domain.physics.{Positionable, TwoDimensional}
import core.domain.species.herbivores.WanderingHerbivore
import core.domain.species.plants.{BasicPlant, InvasivePlant}
import core.domain.species.{Carnivore, Carrion, Egg, Grass, Insect}
import drawers.species.{PlantDrawer, CarnivoreDrawer, CarrionDrawer, GrassDrawer, HerbivoreDrawer, InsectDrawer, InsectEggDrawer}
import org.scalajs.dom

object GenericEntityDrawer extends EntityDrawer[Entity] {
  //private val basicPlantDrawer = BasicPlantDrawer(ctx)


  //FIXME Faire une map (Entity, EntityDrawer) pour eviter le case à rallonge
  override def draw(ctx: dom.CanvasRenderingContext2D, entity: Entity, remainingLag: Double): Unit = {
    entity match {
      //case e: Grass => GrassDrawer.draw(ctx, e, remainingLag)
      case e: InvasivePlant => PlantDrawer.draw(ctx, e, remainingLag)
      case e: WanderingHerbivore => HerbivoreDrawer.draw(ctx, e, remainingLag)
      case e: Carrion => CarrionDrawer.draw(ctx, e, remainingLag)
      case e: Insect => InsectDrawer.draw(ctx, e, remainingLag)
      case e: Egg => InsectEggDrawer.draw(ctx, e, remainingLag)
      case e: Carnivore => CarnivoreDrawer.draw(ctx, e, remainingLag)
      case e: Positionable => {
        ctx.beginPath()
        ctx.arc(e.pos.x, e.pos.y, 20, 0, 2 * Math.PI)
        ctx.fillStyle = "red"
        ctx.fill()
      }
    }
  }
}

