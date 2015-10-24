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
    get[Int]("status") ~
    get[Date]("created") map {  case id ~ title ~ status ~ created =>
      Incident(Some(id), title, Status.fromInt(status).get, created)
    }
  }

  def all(): Seq[Incident] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM incidents ORDER BY id DESC").as(rowParser.*)
  }

  def findById(id: Long): Option[Incident] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM incidents WHERE id = {id}").on('id -> id).as(rowParser.singleOpt)
  }

  def create(incident: IncidentData): Option[Long] = DB.withConnection { implicit c =>
    SQL(s"INSERT INTO incidents (title, status, created) VALUES ({title}, {status}, {created})")
      .on(
        'title -> incident.title,
        'status -> Active.intValue,
        'created -> new Date
      ).executeInsert()
  }
}
