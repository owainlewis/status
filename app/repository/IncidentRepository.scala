package repository

import java.util.Date

import anorm.SqlParser._
import anorm._
import forms.IncidentData
import model._
import play.api.Play.current
import play.api.db.DB

import scala.collection.immutable.ListMap

trait IncidentRepositoryFunctions {

  private val rowParser: RowParser[Incident] = {
    get[Long]("id") ~
    get[String]("title") ~
    get[Option[String]]("description") ~
    get[Int]("status") ~
    get[Date]("created") map {  case id ~ title ~ description ~ status ~ created =>
      Incident(Some(id), title, description, Status.fromInt(status).get, created)
    }
  }

  /**
   * Return all incidents from the database
   * 
   * TODO (add ability to filter by date range as well as number)
   */
  def all(limit: Int = 50): Seq[Incident] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM incidents ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  type IncidentDate = String

  def allGroupedByDate(limit: Int = 100): Map[IncidentDate, Seq[Incident]] =
    ListMap(all().groupBy(_.dateForGrouping).toSeq.sortWith(_._1 > _._1):_*).toMap

  /**
   * Find a single incident by ID
   */
  def findById(id: Long): Option[Incident] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM incidents WHERE id = {id}")
      .on('id -> id).as(rowParser.singleOpt)
  }

  /**
   * Return the number of active incidents in the database
   */
  def active(): Seq[Incident] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM incidents WHERE status != {status}")
      .on('status -> Resolved.intValue).as(rowParser.*)
  }

  def update(incidentId: Long, data: IncidentData): Option[Int] = DB.withConnection { implicit c =>
    findById(incidentId) map { incident =>
      SQL("UPDATE incidents SET title={title}, description={description}, status={status} WHERE id={id}")
        .on('title -> data.title,
          'description -> data.description,
          'status -> data.status,
          'id -> incidentId)
        .executeUpdate()
    }
  }

  /**
   * Create a new incident
   */
  def create(incident: IncidentData): Option[Long] = DB.withConnection { implicit c =>
    val fields = "(title, description, status, created)"
    val values = "({title}, {description}, {status}, {created})"
    SQL(s"INSERT INTO incidents $fields VALUES $values")
      .on(
        'title -> incident.title,
        'description -> incident.description,
        'status -> incident.status,
        'created -> new Date
      ).executeInsert()
  }

  def delete(id: Long) = DB.withConnection { implicit c =>
    SQL("DELETE FROM incidents where id = {id}").on('id -> id).execute
  }
}

object IncidentRepository extends IncidentRepositoryFunctions
