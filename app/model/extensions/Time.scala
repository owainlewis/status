package model.extensions

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

trait Time {

  val created: java.util.Date

  private val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  private val YMDFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  def prettyDate: String = {
    format.print(new DateTime(this.created))
  }

  def dateForGrouping: String = {
    YMDFormat.print(new DateTime(this.created))
  }
}
