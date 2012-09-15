package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String, estimate: Int, done: Boolean)

object Task {

  val task = {
    get[Long]("id") ~
    get[String]("label") ~
    get[Int]("estimate") ~
    get[Boolean]("done") map {
      case id~label~estimate~done => Task(id, label, estimate, done)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(task: (String, Int)) {
    val (label, estimate) = task
    DB.withConnection { implicit c =>
      SQL("insert into task (label, estimate) values ({label}, {estimate})").on(
        'label -> label,
	'estimate -> estimate
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  def complete(id: Long) { 
    DB.withConnection { implicit c =>
      SQL("update task set done = true where id = {id}").on(
	'id -> id
      ).executeUpdate()
    }
  }
}
