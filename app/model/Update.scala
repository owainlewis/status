package model

import java.util.Date

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath

case class Update(id: Option[Long], incident: Long, title: String, description: Option[String], created: java.util.Date)
  extends extensions.Time

object Update {
  implicit val writer = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "incident").write[Long] and
      (JsPath \ "title").write[String] and
      (JsPath \ "description").writeNullable[String] and
      (JsPath \ "created").write[Date]
    )(unlift(Update.unapply))

  implicit val reader = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "incident").read[Long] and
      (JsPath \ "title").read[String] and
      (JsPath \ "description").readNullable[String] and
      (JsPath \ "created").read[Date]
    )(Update.apply _)
}
