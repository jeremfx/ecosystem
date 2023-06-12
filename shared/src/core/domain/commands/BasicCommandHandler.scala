package core.domain.commands

import core.domain.game.EntityRepository
import core.domain.species.BasicPlant

class BasicCommandHandler(entityRepo : EntityRepository) extends CommandHandler {
  def handle(command: Command): Unit = {
    command match {
      case c:CreateBasicPlant => entityRepo.add(new BasicPlant(1, entityRepo, c.pos, BasicPlant.MIN_RADIUS))
      case c:RemoveBasicPlant => entityRepo.plants().find(p => p.contains(c.pos))
    }
  }
}
