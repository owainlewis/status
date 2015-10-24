package repository

import anorm.SqlParser._
import anorm._
import model._
import java.util.Date
import play.api.db.DB
import play.api.Play.current
import forms.IncidentData

object IncidentRepository {

  private val rowParser: RowParser[Incident] = {
    get[Long]("id") ~
    get[String]("title") ~
    get[String]("description") ~
    get[Int]("status") ~
    get[Date]("created") map {  case id ~ title ~ description ~ status ~ created =>
      Incident(Some(id), title, description, Status.fromInt(status).get, created)
    }
  }

  /**
   * Return all incidents from the database
   *
   */
  def all(limit: Int = 100): Seq[Incident] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM incidents ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  /**
   * Find a single incident by ID
   *
   */
  def findById(id: Long): Option[Incident] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM incidents WHERE id = {id}")
      .on('id -> id).as(rowParser.singleOpt)
  }

  /**
   * Return the number of active incidents in the database
   *
   */
  def active(): Seq[Incident] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM incidents WHERE status = {status}")
      .on('status -> Active.intValue).as(rowParser.*)
  }

  /**
   * Create a new incident
   *
   */
  def create(incident: IncidentData): Option[Long] = DB.withConnection { implicit c =>
    SQL(s"INSERT INTO incidents (title, description, status, created) VALUES ({title}, {description}, {status}, {created})")
      .on(
        'title -> incident.title,
        'description -> incident.description,
        'status -> Active.intValue,
        'created -> new Date
      ).executeInsert()
  }

  def delete(id: Long) = DB.withConnection { implicit c =>
    SQL("DELETE FROM incidents where id = {id}").on('id -> id).execute
  }
}
