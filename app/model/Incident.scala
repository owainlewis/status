package model

sealed trait Status

case object Open extends Status
case object Closed extends Status

case class Incident(id: Option[Long], title: String)
