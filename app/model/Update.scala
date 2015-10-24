package model

import play.api.libs.json.{Json, JsValue, Writes}

case class Update(id: Option[Long], incident: Long, title: String, description: String, created: java.util.Date)
  extends extensions.Time

object Update {
  implicit val writer = new Writes[Update] {
    def writes(update: Update): JsValue =
      Json.obj("title" -> update.title,
               "description" -> update.description,
               "created" -> update.prettyDate)
  }
}
