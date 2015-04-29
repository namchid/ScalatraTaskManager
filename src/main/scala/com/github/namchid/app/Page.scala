package com.github.namchid.app

import org.scalatra._
import scalate.ScalateSupport
import scala.xml.{ Text, Node }

import scala.slick.driver.MySQLDriver.simple.Database
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation

import Tables._

object Page {
  
  //todo: add multiple users, deleteTask, addTask

  val loginHeader = {
    <title>"Scalatra Task Manager Login"</title>
    <link rel="stylesheet" type="text/css" href="css/tasks.css"/>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Raleway:600,300"/>
    <link rel="stylesheet" type="text/css" href="css/loginStyles.css"/>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
  }

  val tasksHeader = {
    <title>Scalatra Task Manager</title>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Raleway:600,300"/>
    <link rel="stylesheet" type="text/css" href="css/tasks.css"/>
    <link rel="stylesheet" type="text/css" href="styles/myTasksStyles.css"/>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
  }

  def getTasks(db: Database, userId: Int): Seq[Node] = {
    db.withSession {
      implicit session =>
        val usersTasks = tasks.filter(i => i.userId === 1).list

        val retTasks = usersTasks.map(x =>
          <tr>
            <td class="addDeleteCol">
              <input type="submit" value={ x.taskId.toString() } name="delete" class="butt"/>
            </td>
            <td>
              { x.description }
            </td>
          </tr>)
        return retTasks
    }
  }

  def deleteTask(taskId: Int, userId: Int) {
    
  }

  def addTask(taskId: Int, userId: Int) {

  }

  def set(contents: Seq[Node]) = {
    <html>
      <head lang="en">
        { loginHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class="">
          { contents }
        </div>
      </body>
    </html>
  }

  def set(db: Database, userId: Int = -1, username: String = "") = {
    val retTasks = userId match {
      case -1 => <h1></h1>
      case _ =>
        getTasks(db, userId)
    }
    <html>
      <head lang="en">
        { tasksHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class="outerContainer">
          <h1>Hello, { username }</h1>
          <form action="/addDelete" method="post">
            <table>
              { retTasks }
              <tr>
                <td class="addDeleteCol">
                  <input type="submit" value="+" name="add" class="addButt"/>
                </td>
                <td>
                  <input type="text" name="newTask" size="30" maxLength="100"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </body>
    </html>
  }
}
