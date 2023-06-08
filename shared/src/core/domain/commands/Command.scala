package core.domain.commands

import core.domain.physics.Vec

sealed trait Command

case class CreateBasicPlant(pos: Vec) extends Command
case class RemoveBasicPlant(pos: Vec) extends Command