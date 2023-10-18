package events

import org.scalajs.dom
import org.scalajs.dom.MouseEvent

trait DomEventHandler {
  def onMouseDown(e: dom.MouseEvent): Unit
  def onMouseUp(e: dom.MouseEvent): Unit
  def onClick(e: dom.MouseEvent): Unit
  def onKeyDown(e: dom.KeyboardEvent): Unit
  def onKeyPress(e: dom.KeyboardEvent): Unit
  def onKeyUp(e: dom.KeyboardEvent): Unit
  def onDoubleClick(e: MouseEvent): Unit
}
