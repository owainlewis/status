package integrations

import config.Config
import forms.IncidentData

/**
 * Handles the dispatch of all outbound notifications
 *
 * TODO this is mess. Clean up when I get time (quick hack for testing)
 *
 * 1. Slack
 * 2. SMS
 * 3. Email
 */
object Dispatcher {
  def dispatch(data: IncidentData) = {
    if (Config.slackEnabled) {
      Config.slackURL foreach { url =>
        new Slack(url).notify(Notification(data.title))
      }
    }
  }
}
