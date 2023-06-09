package server

import scalatags.Text
import scalatags.Text.all._
import scalatags.Text.tags2.title

object Server extends cask.MainRoutes {

  override def host: String = "0.0.0.0"

  override def port: Int = 8001

  var openConnections = Set.empty[cask.WsChannelActor]

/*  def subscribe() = cask.WsHandler { connection =>
    connection.send(cask.Ws.Text(messageList().render))
    openConnections += connection
    cask.WsActor { case cask.Ws.Close(_, _) => openConnections -= connection }
  }*/

  @cask.get("/")
  def index(): Text.all.doctype = {
    doctype("html")(
      html(
        head(
          meta(charset := "UTF-8"),
          title("Game starter")
        ),
        body(style := " margin: 0; font-size: 0;")(
          canvas(id := "gameCanvas", tabindex := 1, style := "margin-left:auto; margin-right:auto; display: block;"),
          script(src := "js/main.js"),
          script("App.start()")
        )
      )
    )
  }

/*  @cask.get("/test")
  def get(): Text.all.doctype = {
    doctype("html")()
  }*/


  @cask.staticFiles("/js/")
  def staticFileRoutes() = "out/client/fastLinkJs.dest/"

  initialize()
}
