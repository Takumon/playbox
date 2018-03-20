package controllers


import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.{ Inject, Singleton }
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport }
import play.api.data.Form
import play.api.data.Forms.{ mapping, text }
import play.filters.csrf.CSRF

import daos.TodoDao
import models.Todo


case class TodoForm(action: String, content: String)

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class IndexController @Inject()(components: ControllerComponents, todoDao: TodoDao) extends AbstractController(components)
  with I18nSupport {

  val todoForm = Form(
    mapping(
      "action" -> text,
      "content" -> text
    )(TodoForm.apply)(TodoForm.unapply)
  )

  /** 初期表示 */
  def get = Action.async { implicit request =>
    val token = CSRF.getToken(request)
    todoDao.all().map(todos => Ok(views.html.index(todoForm, todos)))
  }

  /** アクション(POST) */
  def post = Action.async { implicit request =>
    val token = CSRF.getToken(request)
    todoForm.bindFromRequest.fold(
      formWithErrors => {
        todoDao.all().map(todos => Ok(views.html.index(formWithErrors, todos)))
      },
      todoData => {
        val action = todoData.action.split(":")
        action(0) match {
          case "insert" => insert(todoData)
          case "update" => update(todoData.action.split(":")(1), todoData)
          case "delete" => delete(todoData.action.split(":")(1), todoData)
          case _ => println("No Action!!")
        }
        todoDao.all().map(todos => Ok(views.html.index(todoForm.fill(todoData), todos)))
      }
    )
  }

  /** 登録 */
  def insert(todoForm: TodoForm) = todoDao.insert(Todo(0, todoForm.content))

  /** 更新 */
  def update(id: String, todoForm: TodoForm) = todoDao.update(Todo(id.toLong, todoForm.content))

  /** 削除 */
  def delete(id: String, todoForm: TodoForm) = todoDao.delete(Todo(id.toLong, todoForm.content))

}
