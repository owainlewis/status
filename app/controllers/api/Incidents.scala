package controllers.api

import config.Config
import play.api.mvc._
import repository.IncidentRepository
import play.api.libs.json._
import forms.{IncidentDataWithStatus, IncidentData}

class Incidents extends Controller {

  private val WithBasicAuth = BasicAuthAction(Config.authUser, Config.authPass)

  def index = WithBasicAuth {
    Ok(Json.toJson(IncidentRepository.all()))
  }

  def show(id: Long) = WithBasicAuth {
    IncidentRepository.findById(id) map { incident =>
      Ok(Json.toJson(incident))
    } getOrElse NotFound
  }

  private def createIncident(json: JsValue): Status = {
    json.asOpt[IncidentData].map { incident =>
      IncidentRepository.create(incident)
      Ok
    }.getOrElse {
      BadRequest
    }
  }

  def create = WithBasicAuth { implicit request =>
    request.body.asJson.map (json => createIncident(json)) getOrElse BadRequest
  }

  private def updateIncident(id: Long, json: JsValue): Status = {
    json.asOpt[IncidentDataWithStatus].map { dataWithStatus =>
      IncidentRepository.update(id, dataWithStatus)
      Ok
    }.getOrElse { BadRequest }
  }

  def update(id: Long) = WithBasicAuth { implicit request =>
    request.body.asJson.map (json => updateIncident(id, json)) getOrElse BadRequest
  }

  def delete(id: Long) = WithBasicAuth {
    IncidentRepository.findById(id) map { _ =>
      IncidentRepository.delete(id)
      Ok
    } getOrElse NotFound
  }
}
