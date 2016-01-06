package controllers

import play.api.mvc._
import repository.IncidentRepository

class Reports extends Controller {
  /**
    * Show public facing incident reports
    *
    * Summary of incidents for this last 7 days (keep it simple for now)
    */
  def index = Action { implicit request =>
    val incidents = IncidentRepository.allIncidentsThisWeek()
    Ok(views.html.reports.index(incidents))
  }
}
