package forms

import play.api.data.Form
import play.api.data.Forms._

object Forms {
  
  val incidentForm = Form(
    mapping("title" -> nonEmptyText, "description" -> optional(text))
      (IncidentData.apply)(IncidentData.unapply))

  val incidentFormWithStatus = Form(
    mapping("title" -> optional(text), "description" -> optional(text), "status" -> optional(number))
      (IncidentDataWithStatus.apply)(IncidentDataWithStatus.unapply)
  )

  val updateForm = Form(
    mapping("incident" -> longNumber, "title" -> nonEmptyText, "description" -> text)
      (UpdateData.apply)(UpdateData.unapply))
}
