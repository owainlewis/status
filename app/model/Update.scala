package model

case class Update(id: Option[Long], incident: Long, title: String, description: String)
