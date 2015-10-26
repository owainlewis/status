package config

import play.api.Play

object Config {
  
  private def getString(k: String): Option[String] = 
    Play.current.configuration.getString(k)

  private def getOr(k: String, v: String) = 
    getString(k).getOrElse(v)

  val backgroundEnabled = 
   Play.current.configuration.getBoolean("background.enabled")
     .getOrElse(false)

  /**
   * The title shown on the main incidents page
   */
  val applicationHeader = getOr("app.title", "operations")

  /**
   * The admin username
   */
  val authUser = getOr("auth.username", "admin")

  /**
   * The admin password
   */
  val authPass = getOr("auth.password", "password")

  val slackEnabled =
    Play.current.configuration.getBoolean("slack.enabled")
      .getOrElse(false)

  val slackURL = getString("slack.endpoint")
}
