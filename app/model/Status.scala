package model

sealed trait Status {
  val intValue: Int
  val name: String
  val iconClass: String
}

object Status {
  def fromInt(n: Int): Option[Status] = n match {
    case 1 => Some(Investigating)
    case 2 => Some(Identified)
    case 3 => Some(Monitoring)
    case 4 => Some(Resolved)
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
