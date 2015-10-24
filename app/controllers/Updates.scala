package controllers

import play.api.mvc._

class Updates extends Controller {

  def add(incident: Long) = Action {
    Ok(views.html.updates.add(incident))
  }

  def create = Action {
   NotImplemented
  }
}
