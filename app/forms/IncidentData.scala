package forms

import play.api.libs.json._

case class IncidentData(title: String, description: Option[String], status: Int)

object IncidentData {
  implicit val format = Json.format[IncidentData]
}
