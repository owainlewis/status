package model

import play.api.libs.json._
import repository.UpdateRepository

case class Incident(id: Option[Long],
                    title: String,
                    description: Option[String],
                    status: Status,
                    created: java.util.Date) extends extensions.Time {

  def updates: Seq[Update] =
    this.id map { UpdateRepository.all(_) } getOrElse Nil
}

object Incident {
  implicit val writer = new Writes[Incident] {
    def writes(incident: Incident): JsValue = {
      val updates = incident.id map (incident => UpdateRepository.all(incident)) getOrElse Nil
      Json.obj("title" -> incident.title,
               "description" -> incident.description,
               "status" -> incident.status.name,
               "created" -> incident.prettyDate,
               "updates" -> Json.toJson(updates))
    }
  }
}
