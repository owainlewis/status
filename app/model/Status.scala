package model

sealed trait Status {
  val intValue: Int
  val name: String
}

object Status {
  def fromInt(n: Int): Option[Status] = n match {
    case 1 => Some(Active)
    case 2 => Some(Ongoing)
    case 3 => Some(Resolved)
    case _ => None
  }

  def toInt(status: Status): Option[Int] = status match {
    case Active => Some(1)
    case _ => None
  }

  val allStatuses = List(Active, Ongoing, Resolved)
}

case object Active   extends Status { val intValue = 1; val name = "Active"   }
case object Ongoing  extends Status { val intValue = 2; val name = "Ongoing"  }
case object Resolved extends Status { val intValue = 3; val name = "Resolved" }
