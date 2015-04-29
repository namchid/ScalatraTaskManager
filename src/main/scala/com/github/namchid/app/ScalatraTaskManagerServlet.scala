package com.github.namchid.app

import org.scalatra._
import scala.xml.{ Text, Node }
import scalate.ScalateSupport
import scala.slick.driver.MySQLDriver.simple._

import Page._
import Tables._

class ScalatraTaskManagerServlet(db: Database) extends ScalatraTaskManagerWebAppStack with FlashMapSupport with ScalateSupport {

  def checkUsername(username: String, password: String): Int = {
    db.withSession {
      implicit session =>
        val filteredUsers = users.filter(x => x.username === username).list

        if (filteredUsers.length < 1) {
          redirect("/")
        } else {
          val correctUser = filteredUsers(0)
          if (password != correctUser.password) {
            redirect("/")
          } else {
            return correctUser.userId
          }
        }
    }
  }

  get("/") {
    session.get("user_id") match {
      case None =>
        Page.set()
      case _ =>
        redirect("/tasks")
    }
  }

  post("/tasks") {
    (params.get("username"), params.get("password")) match {
      case (None, None) =>
        redirect("/")
      case _ =>
        session("user_id") = checkUsername(params("username"), params("password"))
        session("username") = params("username")
        redirect("/tasks")
    }
  }

  get("/tasks") {
    session.get("user_id") match {
      case (None) =>
        redirect("/")
      case _ =>
        val userId = session("user_id").asInstanceOf[Int]
        set(db, userId, { session("username").toString() })
    }
  }

  //todo you are here
  post("/addDelete") {
    <html>
      <body>
        Ok you got here
        {
          println("params:")
          println(params)
          println(params("newTask"))
        }
      </body>
    </html>
  }

  get("/logout") {
    session.invalidate()
    redirect("/")
  }

}

