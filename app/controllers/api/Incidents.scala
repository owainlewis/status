package controllers.api

import play.api.mvc._
import repository.IncidentRepository
import play.api.libs.json._
import forms.IncidentData

class Incidents extends Controller {

  def index = Action {
    Ok(Json.toJson(IncidentRepository.all()))
  }

  def show(id: Long) = Action {
    IncidentRepository.findById(id) map { incident =>
      Ok(Json.toJson(incident))
    } getOrElse NotFound
  }

  private def createIncident(json: JsValue) = {

    json.asOpt[IncidentData].map { incident =>
      IncidentRepository.create(incident)
      Created.as("application/json")
    }.getOrElse {
      BadRequest.as("application/json")
    }
  }

  def create = Action { implicit request =>
    request.body.asJson.map (json => createIncident(json)) getOrElse BadRequest
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
    IncidentRepository.delete(id)
    Ok
  }
}
