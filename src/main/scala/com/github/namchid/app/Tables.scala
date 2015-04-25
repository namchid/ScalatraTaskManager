package com.github.namchid.app

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.GetResult

case class UserCC (
    username: String, 
    password: String,
    userId: Int
)

case class TaskCC (
	taskId: Int,
	userId: Int,
	description: String
)

class User(tag: Tag) extends Table[UserCC](tag, "user") {
  def username = column[String]("username")
  def password = column[String]("password")
  def userId = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
  
  def * = (username, password, userId) <> ((UserCC.apply _).tupled, (UserCC).unapply)
}

class Task(tag: Tag) extends Table[TaskCC](tag, "task") {
	def taskId = column[Int]("task_id", O.PrimaryKey, O.AutoInc)
	def userId = column[Int]("user_id")
	def description = column[String]("description")
	
	def * = (taskId, userId, description) <> ((TaskCC.apply _).tupled, (TaskCC.unapply))
}

object Tables {
  val users = TableQuery[User]
  val tasks = TableQuery[Task]
}