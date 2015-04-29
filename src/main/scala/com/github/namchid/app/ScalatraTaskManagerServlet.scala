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

  get("/newUser") {
    setNewUserForm()
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
    println("session variables\n" + session.toList)

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
    println(params)
    
    
    (params.get("delete"), params.get("add")) match {
      case (None, Some(_)) =>
        val userId = session("user_id").asInstanceOf[Int]
        if (params("newTask") == "")
          redirect("/tasks")
        addTask(db, { params("newTask") }, userId)
      case (Some(_), None) =>
        deleteTask(db, { params("delete").toInt })
      case _ =>
    }
    redirect("/tasks")
  }

  post("/createNewUser") {
    println(params)

    val username = params.get("username")
    val password = params.get("password")
    val password2 = params.get("password2")

    if (username == Some("") || password == Some("") || password2 == Some(""))
      redirect("/newUser")
    else {
      if (password != password2)
        redirect("/newUser")
      createNewUser(db, { params("username").toString() }, { params("password").toString() })
      redirect("/newUserConfirmationPage")
    }
  }

  get("/newUserConfirmationPage") {
    println("todo starting here")
    setConfirmation()
  }

  get("/logout") {
    session.invalidate()
    redirect("/")
  }

}

