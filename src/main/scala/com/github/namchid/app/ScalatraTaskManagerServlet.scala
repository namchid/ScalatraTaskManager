package com.github.namchid.app

import org.scalatra._
import scala.xml.{ Text, Node }
import scalate.ScalateSupport
import scala.slick.driver.MySQLDriver.simple._

import Page.set
import Tables._

class ScalatraTaskManagerServlet(db: Database) extends ScalatraTaskManagerWebAppStack with FlashMapSupport with ScalateSupport {

  def checkUsername(username: String, password: String) {
    db.withSession {
      implicit session =>
        val filteredUsers = users.filter(x => x.username === username).list

        if(filteredUsers.length < 1) {
          redirect("/")
        } else {
          val correctUser = filteredUsers(0)
          if(password != correctUser.password) {
            redirect("/")
          }
        }
        
    }
  }

  get("/") {
    (session.get("username"), session.get("password")) match {
      case (Some(_), Some(_)) =>
        redirect("/tasks")
      case _ =>
        val contents = {
          <form action={ url("/tasks") } method='POST'>
            <table>
              <tr>
                <td class='right'>username</td>
                <td><input type='text' name='username'/></td>
              </tr>
              <tr>
                <td class='right'>password</td>
                <td><input type='password' name='password'/></td>
              </tr>
              <tr>
                <td colspan='2' id='centered'><button type="submit">ENTER</button></td>
              </tr>
            </table>
          </form>
        }
        set(contents, "noclass")
    }
  }

  post("/tasks") {
    // check username
    checkUsername(params("username"), params("password"))

    (session.get("username"), session.get("password")) match {
      case (None, None) =>
        session("username") = params("username")
        session("password") = params("password") //ok, clearly I shouldn't be storing this. get index later instead
        val contents = {
          <h1>You posted:</h1>
          <h2>{ session("username") }</h2>
          <h2>{ session("password") }</h2>
        }
        set(contents, "none")
      case _ =>
        redirect("/tasks")
    }
  }

  get("/tasks") {
    (session.get("username"), session.get("password")) match {
      case (None, None) =>
        redirect("/")
      case _ =>
        val contents = {
          <h1>You already logged in.</h1>
          <h2>{ session("username") }</h2>
          <h2>{ session("password") }</h2>
        }
        set(contents, "none")
    }
  }

  get("/logout") {
    session.invalidate()
    redirect("/")
  }

}

