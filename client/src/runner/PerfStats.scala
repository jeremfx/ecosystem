package runner

import org.scalajs.dom

import scala.collection.mutable

class PerfStats() {
  private val tsOfDraws = new mutable.ListBuffer[Double]()
  private val tsOfUpdates = new mutable.ListBuffer[Double]()
  private var numberOfEntities = 0

  def logDraw(timestamp: Double): Unit = tsOfDraws += timestamp
  def logUpdate(timestamp: Double) : Unit = tsOfUpdates += timestamp
  def logNumberOfEntites(value: Int) : Unit = numberOfEntities = value


  def drawStats(timestamp: Double, ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.font = "15px Courier";
    ctx.fillStyle = "red"
    ctx.fillText("FPS : " + String.valueOf(getFps(timestamp)), 10, 10)
    ctx.fillText("UPS : " + String.valueOf(getUps(timestamp)), 115, 10)
    ctx.fillText("Entities : " + String.valueOf(numberOfEntities), 220, 10);
    if(tsOfDraws.length > 1000){
      tsOfDraws.dropInPlace(500)
    }
    if(tsOfUpdates.length > 1000){
      tsOfUpdates.dropInPlace(500)
    }
  }

  private def getFps(timestamp: Double) = {
    tsOfDraws.count(ts => ts > timestamp - 3000)/3
  }

  private def getUps(timestamp: Double) = {
    tsOfUpdates.count(ts => ts > timestamp - 3000)/3
  }

}

