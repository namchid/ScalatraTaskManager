package com.github.namchid.app

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.GetResult

case class User (
    username: String, 
    password: String,
    userId: Int
)

class Users(tag: Tag) extends Table[User](tag, "user") {
  def username = column[String]("username")
  def password = column[String]("password")
  def userId = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
  
  def * = (username, password, userId) <> ((User.apply _).tupled, (User).unapply)
}

object Tables {
  val users = TableQuery[Users]

}