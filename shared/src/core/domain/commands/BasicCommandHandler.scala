package core.domain.commands

import core.domain.game.EntityRepository
import core.domain.species.BasicPlant

class BasicCommandHandler(entityRepo : EntityRepository) extends CommandHandler {
  def handle(command: Command): Unit = {
    command match {
      case c:CreateBasicPlant => entityRepo.add(new BasicPlant(1, c.pos, 2000))
      case c:RemoveBasicPlant => entityRepo.entities().collect({case p:BasicPlant => p}).find(p => p.contains(c.pos))
    }
  }
}
