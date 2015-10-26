package forms

import play.api.libs.json._

case class IncidentData(title: String, description: Option[String])

object IncidentData {
  implicit val format = Json.format[IncidentData]
}

case class IncidentDataWithStatus(title: Option[String], description: Option[String], status: Option[Int])

object IncidentDataWithStatus {
  implicit val format = Json.format[IncidentDataWithStatus]
}


