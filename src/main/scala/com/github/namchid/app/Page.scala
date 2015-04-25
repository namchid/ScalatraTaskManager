package com.github.namchid.app

import org.scalatra._
import scalate.ScalateSupport
import scala.xml.{ Text, Node }

import scala.slick.driver.MySQLDriver.simple.Database
import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation

import Tables._

object Page {

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
    <link rel="stylesheet" type="text/css" href="css/loginStyles.css"/>
    <link rel="stylesheet" type="text/css" href="css/tasks.css"/>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
  }

  def getTasks(db: Database, userId: Int) = {
    db.withSession {
      implicit session =>
        //val usersTasks = tasks.filter(i => i.userId == 1)
    }
  }

  def set(contents: Seq[Node], containerClassName: String = "outerContainer") = {
    <html>
      <head lang="en">
        { loginHeader }
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class={ containerClassName }>
          { contents }
        </div>
      </body>
    </html>
  }
}
