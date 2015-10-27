package repository

import anorm.SqlParser._
import anorm._
import model._
import java.util.Date
import play.api.db.DB
import play.api.Play.current

object UpdateRepository {
  private val rowParser: RowParser[Update] = {
    get[Long]("id") ~
    get[Long]("incident") ~
    get[String]("title") ~
    get[Option[String]]("description") ~
    get[Date]("created") map {  case id ~ incident ~ title ~ description ~ created =>
      Update(Some(id), incident, title, description, created)
    }
  }

  /**
   * Return all updates for a given incident
   */
  def all(incident: Long, limit: Int = 100): Seq[Update] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM updates WHERE incident = {incident} ORDER BY id DESC LIMIT {limit}")
      .on('incident -> incident, 'limit -> limit).as(rowParser.*)
  }

  /**
   * Find a single update by ID
   */
  def findById(id: Long): Option[Update] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM updates WHERE id = {id}")
      .on('id -> id).as(rowParser.singleOpt)
  }

  /**
   * Create a new update for an incident
   */
  def create(incident: Long, title: String, description: String): Option[Long] =
    DB.withConnection { implicit c =>
      val fields = "(incident, title, description, created)"
      val values = "({incident}, {title}, {description}, {created})"
      SQL(s"INSERT INTO updates $fields VALUES $values")
      .on(
        'incident -> incident,
        'title -> title,
        'description -> description,
        'created -> new Date
      ).executeInsert()
  }
}
