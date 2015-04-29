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
    <link rel="stylesheet" type="text/css" href="css/myTasksStyles.css"/>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
  }

  val loginContents = {
    <form action="/tasks" method='POST'>
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
        <tr>
          <td colspan='2' id="centered"><a href="/newUser">Create New Account</a></td>
        </tr>
      </table>
    </form>
  }

  val newUserForm = {
    <form action="/createNewUser" method="POST">
      <table>
        <tr>
          <td class="right">Enter Username:</td>
          <td><input type='text' name='username'/></td>
        </tr>
        <tr>
          <td class='right'>Enter password:</td>
          <td><input type='password' name='password'/></td>
        </tr>
        <tr>
          <td class='right'>Re-enter password:</td>
          <td><input type='password' name='password2'/></td>
        </tr>
        <tr>
          <td colspan='2' id='centered'><button type="submit">Submit</button></td>
        </tr>
      </table>
    </form>
  }

  def getTasks(db: Database, userId: Int): Seq[Node] = {
    db.withSession {
      implicit session =>
        val usersTasks = tasks.filter(i => i.userId === userId).list

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

  def deleteTask(db: Database, taskId: Int) {
    db.withSession {
      implicit session =>
        tasks.filter(t => t.taskId === taskId).delete
    }
  }

  def addTask(db: Database, taskDescription: String, userId: Int) {
    db.withSession {
      implicit session =>
        tasks.map(t => (t.userId, t.description)).insert(userId, taskDescription)
    }
  }

  def createNewUser(db: Database, username: String, password: String) {
    db.withSession {
      implicit session =>
        users.map(u => (u.username, u.password)).insert(username, password)
    }
  }

  def setNewUserForm() = {
    <html>
      <head lang="en">
        { loginHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class="">
          { newUserForm }
        </div>
      </body>
    </html>
  }

  def setConfirmation() = {
    <html>
      <head lang="en">
        { tasksHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class="outerContainer">
          <h1>New Account Created.</h1>
          <h2><a href="/">Click here to return to Login</a></h2>
        </div>
      </body>
    </html>
  }

  def set() = {
    <html>
      <head lang="en">
        { loginHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class="">
          { loginContents }
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
          <div class="headContainer">
            <h1>Hello, { username }</h1>
          </div>
          <a href="/logout"><button type="button">Logout</button></a>
          <hr/>
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
