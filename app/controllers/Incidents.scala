package controllers

import integrations.{Dispatcher, Slack}
import play.api.mvc._
import forms.Forms._
import config.Config

import repository.{UpdateRepository, IncidentRepository}

class Incidents extends Controller with Secured {

  private val slack = new Slack(Config.slackURL.get)
  
  def index = Action { implicit request =>
    val incidents = IncidentRepository.allGroupedByDate()
    val active    = IncidentRepository.active().length
    Ok(views.html.incidents.index(incidents, active))
  }

  def show(id: Long) = Action { implicit request =>
    IncidentRepository.findById(id) map { incident =>
      val updates = UpdateRepository.all(id)
      Ok(views.html.incidents.show(incident, updates))
    } getOrElse {
      NotFound(s"Could not find incident with id $id")
    }
  }

  // Secured actions

  def add = withAuth { _ => implicit request =>
    Ok(views.html.internal.incidents.add(incidentForm))
  }

  def edit(id: Long) = withAuth { _ => implicit request =>
    IncidentRepository.findById(id) map { incident =>
      Ok(views.html.internal.incidents.edit(incident, incidentForm))
    } getOrElse {
      NotFound
    }
  }

  def update(id: Long) = withAuth { _ => implicit request =>
    incidentForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.Incidents.edit(id))
      },
      incidentDataWithStatus => {
        IncidentRepository.update(id, incidentDataWithStatus)
        Redirect(routes.Incidents.index())
      }
    )
  }

  def create = withAuth { _ => implicit request =>
    incidentForm.bindFromRequest.fold(
      formWithErrors => { Redirect(routes.Incidents.add()) },
      incidentData   => {
        // Send web hook and other notifications
        Dispatcher.dispatch(incidentData)
        IncidentRepository.create(incidentData)
        Redirect(routes.Incidents.index())
      }
    )
  }
}
