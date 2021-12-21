package events

import org.scalajs.dom

trait DomEventHandler {
  def onMouseDown(e: dom.MouseEvent)
  def onMouseUp(e: dom.MouseEvent)
  def onClick(e: dom.MouseEvent)
  def onKeyDown(e: dom.KeyboardEvent)
  def onKeyPress(e: dom.KeyboardEvent)
  def onKeyUp(e: dom.KeyboardEvent)
}
