package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String, estimate: Int)

object Task {

  val task = {
    get[Long]("id") ~
    get[String]("label") ~
    get[Int]("estimate") map {
      case id~label~estimate => Task(id, label, estimate)
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
}
