package runner

import org.scalajs.dom

import scala.collection.mutable

class PerfStats() {
  private var tsOfDraws = new mutable.ListBuffer[Double]()
  private var tsOfUpdates = new mutable.ListBuffer[Double]()
  private var numberOfEntities = 0

  def logDraw(timestamp: Double): Unit = tsOfDraws += timestamp
  def logUpdate(timestamp: Double) : Unit = tsOfUpdates += timestamp
  def logNumberOfEntites(value: Int) : Unit = numberOfEntities = value


  def drawStats(timestamp: Double, ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.font = "15px Courier";
    ctx.fillStyle = "grey"
    ctx.fillText("FPS : " + String.valueOf(getFps(timestamp)), 10, 10)
    ctx.fillText("UPS : " + String.valueOf(getUps(timestamp)), 115, 10)
    ctx.fillText("Entities : " + String.valueOf(numberOfEntities), 220, 10);
  }

  private def getFps(timestamp: Double) = {
    tsOfDraws.count(ts => ts > timestamp - 3000)/3
  }

  private def getUps(timestamp: Double) = {
    tsOfUpdates.count(ts => ts > timestamp - 3000)/3
  }

}

