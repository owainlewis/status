package model

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._

sealed trait Status {
  val intValue: Int
  val name: String
}

object Status {
  def fromInt(n: Int): Option[Status] = n match {
    case 1 => Some(Active)
    case 2 => Some(Ongoing)
    case 3 => Some(Resolved)
    case _ => None
  }

  def toInt(status: Status): Option[Int] = status match {
    case Active => Some(1)
    case _ => None
  }
}

case object Active   extends Status { val intValue = 1; val name = "Active"   }
case object Ongoing  extends Status { val intValue = 2; val name = "Ongoing"  }
case object Resolved extends Status { val intValue = 3; val name = "Resolved" }

case class Incident(id: Option[Long], title: String, status: Status, created: java.util.Date) {

  val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  def prettyDate: String = {
    format.print(new DateTime(this.created))
  }
}

object Incident {

  implicit val writer = new Writes[Incident] {
    def writes(incident: Incident): JsValue = {
      Json.obj("title" -> incident.title,
               "status" -> incident.status.name,
               "created" -> incident.prettyDate)
    }
  }
}
