package model

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._

case class Incident(id: Option[Long], title: String, description: String, status: Status, created: java.util.Date) {

  private val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  private val YMDFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  def prettyDate: String = {
    format.print(new DateTime(this.created))
  }

  def dateForGrouping: String = {
    YMDFormat.print(new DateTime(this.created))
  }
}

object Incident {

  implicit val writer = new Writes[Incident] {
    def writes(incident: Incident): JsValue = {
      Json.obj("title" -> incident.title,
               "description" -> incident.description,
               "status" -> incident.status.name,
               "created" -> incident.prettyDate)
    }
  }
}
