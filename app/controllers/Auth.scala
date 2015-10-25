package controllers

import model.Login
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

trait Secured {

  def username(request: RequestHeader) = request.session.get(Security.username)

  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login())

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
}

class Auth extends Controller {

  private val loginForm = Form(
    mapping("username" -> nonEmptyText, "password" -> nonEmptyText)(Login.apply)(Login.unapply)
      verifying ("Invalid email or password", credentials => check(credentials))
  )

  /** TODO get these from config */
  private def check(login: Login): Boolean =
    login.username == "admin" && login.password == "1234"

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action {
    Redirect(routes.Incidents.index()).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      login => Redirect(routes.Incidents.index()).withSession(Security.username -> login.username)
    )
  }
}
