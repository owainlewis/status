package controllers

import play.api.mvc._

import forms.Forms.updateForm
import repository.UpdateRepository

class Updates extends Controller with Secured {

  def add(incident: Long) = withAuth { _ => implicit request =>
    Ok(views.html.internal.updates.add(incident, updateForm))
  }

  def create(incident: Long) = withAuth { _ => implicit request =>
    updateForm.bindFromRequest.fold(
      formWithErrors => { Redirect(routes.Updates.add(incident)) },
      data => {
        UpdateRepository.create(data.incident, data.title, data.description)
        Redirect(routes.Incidents.show(data.incident))
      }
    )
  }
}
