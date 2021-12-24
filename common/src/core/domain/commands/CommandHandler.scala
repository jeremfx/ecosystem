package core.domain.commands

trait CommandHandler {
  def handle(command: Command): Unit
}
