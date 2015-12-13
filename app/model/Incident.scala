package model

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import repository.UpdateRepository
import java.util.Date

case class Incident(id: Option[Long],
                    title: String,
                    description: Option[String],
                    status: Status,
                    created: java.util.Date) extends extensions.Time {

  def updates: Seq[Update] =
    this.id map { UpdateRepository.all(_) } getOrElse Nil
}

object Incident {
  implicit val writer: Writes[Incident] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "title").write[String] and
      (JsPath \ "description").writeNullable[String] and
      (JsPath \ "status").write[String] and
      (JsPath \ "created").write[Date] and
      (JsPath \ "updates").write[Seq[Update]]
    )((incident: Incident) =>
    (incident.id, incident.title, incident.description, incident.status.name, incident.created, incident.updates))

  implicit val reader: Reads[Incident] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "title").read[String] and
      (JsPath \ "description").readNullable[String] and
      (JsPath \ "status").read[String] and
      (JsPath \ "created").read[Date] and
      (JsPath \ "updates").read[Seq[Update]]
    )((id: Option[Long],title: String, description: Option[String], status: String, created: Date, update: Seq[Update]) =>
    Incident(id, title, description, Status.fromString(status).getOrElse(Investigating), created))
}
