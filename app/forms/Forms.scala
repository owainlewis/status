package forms

import play.api.data.Form
import play.api.data.Forms._

object Forms {
  
  val incidentForm = Form(
    mapping("title" -> nonEmptyText, "description" -> optional(text))
      (IncidentData.apply)(IncidentData.unapply))

  val updateForm = Form(
    mapping("incident" -> longNumber, "title" -> nonEmptyText, "description" -> text)
      (UpdateData.apply)(UpdateData.unapply))
}
