package controllers

import play.api.mvc._
import forms.Forms._
import repository.{UpdateRepository, IncidentRepository}

class Incidents extends Controller {

  def index = Action { implicit request =>
    val incidents = IncidentRepository.all().groupBy(_.dateForGrouping)
    val active = IncidentRepository.active().length
    Ok(views.html.incidents.index(incidents, active))
  }

  def add = Action { implicit request =>
    Ok(views.html.internal.incidents.add(incidentForm))
  }

  def show(id: Long) = Action { implicit request =>
    IncidentRepository.findById(id) map { incident =>
      val updates = UpdateRepository.all(id)
      Ok(views.html.incidents.show(incident, updates))
    } getOrElse {
      NotFound(s"Could not find incident with id $id")
    }
  }

  def create = Action { implicit request =>
    incidentForm.bindFromRequest.fold(
      formWithErrors => { Redirect(routes.Incidents.add()) },
      incidentData   => {
        IncidentRepository.create(incidentData)
        Redirect(routes.Incidents.index())
      }
    )
  }
}
