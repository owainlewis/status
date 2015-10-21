package repository

import anorm.SqlParser._
import anorm._
import models.Item
import org.joda.time.DateTime
import play.api.db.DB._
import play.api.Play.current

object IncidentRepository {

  private val rowParser: RowParser[Incident] = {
    get[Long]("id") ~
    get[Long]("title") ~
    get[DateTime]("created") map {
    case id ~ title ~ created =>
      Incident(Some(id), title, created)
    }
  }

  /**
   * Fetch all incidents from the database
   */
  def all(stream: Long): Seq[Item] = withConnection { implicit c =>
    SQL(s"SELECT * FROM incidents ORDER BY id DESC").as(rowParser.*)
  }
}
