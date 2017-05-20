package integrations

import play.api.libs.ws.{WSResponse, WS}
import play.api.libs.json._
import play.api.Play.current

import scala.concurrent.Future

case class Notification(text: String, username: String="statusbot")

object Notification {
  implicit val format = Json.format[Notification]
}

class Slack(url: String) {
  def notify(notification: Notification): Future[WSResponse] =
    WS.url(url).post(Json.toJson(notification))

  def sendText(text: String) = notify(Notification(text))
}
