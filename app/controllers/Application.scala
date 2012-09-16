package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.Task

object Application extends Controller {

  val taskForm = Form(
    tuple(
      "label" -> nonEmptyText,
      "estimate" -> (number verifying("The estimate needs to be between 0 and 5 pomodoros", 
	n => n >= 0 && n <= 5))
    )
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(Task.all(), formWithErrors)),
      task => {
        Task.create(task)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  } 

  def completeTask(id: Long) = Action { 
    Task.complete(id)
    Redirect(routes.Application.tasks)
  }

}
