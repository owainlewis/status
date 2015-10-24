package controllers.api

import play.api.mvc._
import repository.IncidentRepository
import play.api.libs.json._

class Incidents extends Controller {

  def index = Action {
    Ok(Json.toJson(IncidentRepository.all()))
  }

  def show(id: Long) = Action {
    IncidentRepository.findById(id) map { incident =>
      Ok(Json.toJson(incident))
    } getOrElse NotFound
  }

  def create = Action {
    NotImplemented
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
   NotImplemented
  }
}
