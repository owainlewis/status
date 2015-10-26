package controllers

import play.api.mvc._
import repository.IncidentRepository

class Components extends Controller {

  def index = Action { implicit request =>
    val pastIncidents = IncidentRepository.allGroupedByDate()
    Ok(views.html.components.index(pastIncidents))
  }

  def show(id: Long) = Action {
    NotImplemented
  }

  def create = Action {
    NotImplemented
  }

  def update = Action {
    NotImplemented
  }

  def delete = Action {
    NotImplemented
  }
}
