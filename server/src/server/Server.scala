package server
import scalatags.Text
import scalatags.Text.all._
import scalatags.Text.tags2.title


object Server extends cask.MainRoutes {
  @cask.get("/")
  def index(): Text.all.doctype = {
    doctype("html")(
      html(
        head(
          meta(charset:="UTF-8"),
          title("Game starter")
        ),
          body(
            canvas( id:="gameCanvas", width:=1280, height:=720, tabindex:=1, style:="margin-left:auto; margin-right:auto; display: block;"),
            script(src:="js/out.js"),
            script("App.start()")
          )
      )
    )
  }



  @cask.staticFiles("/js/")
  def staticFileRoutes() = "out/js/fastOpt/dest"

  initialize()
}
