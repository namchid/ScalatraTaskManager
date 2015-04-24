import scala.slick.driver.MySQLDriver.simple.Database

import org.scalatra.LifeCycle

import com.github.namchid.app.ScalatraTaskManagerServlet

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val db = Database.forURL("jdbc:mysql://localhost/ndo", user = "ndo", password = "0755406", driver = "com.mysql.jdbc.Driver")

    context.mount(new ScalatraTaskManagerServlet(db), "/*")
  }
}
