package controllers.api

import play.api.mvc._
import scala.concurrent.Future

final class BasicAuthAction(username: String, password: String) extends ActionBuilder[Request] with ActionFilter[Request] {

  private val unauthorized =
    Results.Unauthorized.withHeaders("WWW-Authenticate" -> "Basic realm=Unauthorized")

  def filter[A](request: Request[A]): Future[Option[Result]] = {
    val result = request.headers.get("Authorization") map { authHeader =>
      val (user, pass) = decodeBasicAuth(authHeader)
      if (user == username && pass == password) None else Some(unauthorized)
    } getOrElse Some(unauthorized)

    Future.successful(result)
  }

  private [this] def decodeBasicAuth(authHeader: String): (String, String) = {
    val baStr = authHeader.replaceFirst("Basic ", "")
    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
    val Array(user, password) = new String(decoded).split(":")
    (user, password)
  }
}

object BasicAuthAction {
  def apply(username: String, password: String) = new BasicAuthAction(username, password)
}