package forms

import play.api.data.Form
import play.api.data.Forms._

object Forms {

  val incidentForm = Form(
    mapping("title" -> nonEmptyText, "description" -> text)
      (IncidentData.apply)(IncidentData.unapply)
  )
}
