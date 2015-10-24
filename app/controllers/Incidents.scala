package controllers

import play.api.mvc._
import forms.Forms._
import repository.IncidentRepository

class Incidents extends Controller {

  def index = Action {
    val incidents = IncidentRepository.all().groupBy(_.dateForGrouping)
    val active = IncidentRepository.active().length
    Ok(views.html.incidents.index(incidents, active))
  }

  def add = Action {
    Ok(views.html.incidents.add(incidentForm))
  }

  def show(id: Long) = Action {
    IncidentRepository.findById(id) map { incident =>
      Ok(views.html.incidents.show(incident))
    } getOrElse {
      NotFound(s"Could not find incident with id $id")
    }
  }

  def create = Action { implicit request =>
    incidentForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.Incidents.add())
      },
      incidentData => {
        IncidentRepository.create(incidentData)
        Redirect(routes.Incidents.index())
      }
    )
  }
}
