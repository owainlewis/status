package model

sealed trait Status {
  val intValue: Int
  val name: String
  val iconClass: String
}

object Status {
  def fromInt(n: Int): Option[Status] = n match {
    case Investigating.intValue => Some(Investigating)
    case Identified.intValue => Some(Identified)
    case Monitoring.intValue => Some(Monitoring)
    case Resolved.intValue => Some(Resolved)
    case _ => None
  }

  def fromString(str: String): Option[Status] = str match {
    case Investigating.name => Some(Investigating)
    case Identified.name => Some(Identified)
    case Monitoring.name => Some(Monitoring)
    case Resolved.name => Some(Resolved)
    case _ => None
  }

  val allStatuses = List(Investigating, Identified, Monitoring, Resolved)
}

// investigating identified monitoring resolved

case object Investigating extends Status {
  val intValue = 1
  val name = "Investigating"
  val iconClass = "fa-exclamation-triangle red"
}

case object Identified extends Status {
  val intValue = 2
  val name = "Identified"
  val iconClass = "fa-question-circle yellow"
}

case object Monitoring extends Status {
  val intValue = 3
  val name = "Monitoring"
  val iconClass = "fa-exclamation-triangle red"
}

case object Resolved extends Status {
  val intValue = 4
  val name = "Resolved"
  val iconClass = "fa fa-check green"
}
