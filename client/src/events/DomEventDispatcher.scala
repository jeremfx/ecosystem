package events

import org.scalajs.dom.raw.{KeyboardEvent, MouseEvent}

import scala.collection.mutable.ListBuffer

object DomEventDispatcher extends DomEventHandler {

  private val handlers = ListBuffer[DomEventHandler]()

  def register(eventHandler: DomEventHandler): Unit = {
    handlers += eventHandler
  }

  def unregister(eventHandler: DomEventHandler): Unit = {
    handlers -= eventHandler
  }

  override def onKeyDown(e: KeyboardEvent): Unit = {
    handlers.foreach(_.onKeyDown(e))
  }

  override def onKeyPress(e: KeyboardEvent): Unit = {
    handlers.foreach(_.onKeyPress(e))
  }

  override def onClick(e: MouseEvent): Unit = {
    handlers.foreach(_.onClick(e))
  }

  override def onKeyUp(e: KeyboardEvent): Unit = {
    handlers.foreach(_.onKeyUp(e))
  }

  override def onMouseDown(e: MouseEvent): Unit = {
    handlers.foreach(_.onMouseDown(e))
  }

  override def onMouseUp(e: MouseEvent): Unit = {
    handlers.foreach(_.onMouseUp(e))
  }
}
