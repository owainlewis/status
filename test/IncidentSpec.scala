import java.util.Date

import model.{Incident, Status}
import org.scalacheck.{Gen, Prop, Test}
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.WithApplication

class IncidentSpec extends Specification with BasicConsoleReporter {

  val incidentsGen = for {
    id <- Gen.option(Gen.chooseNum[Long](0, Long.MaxValue))
    title <- Gen.alphaStr
    desc <- Gen.option(Gen.alphaStr)
    status <- Gen.oneOf(Status.allStatuses)
    create <- Gen.chooseNum[Long](0, Long.MaxValue).map(new Date(_))
  } yield Incident(id, title, desc, status, create)

  "Incident" should {

    "be alternate between his json and object form" in new WithApplication() {
      val jsonAlternationLaw =
        Test.check(Test.Parameters.default, Prop.forAll(incidentsGen)(incident => Json.toJson(incident).as[Incident] == incident))
      reporter.onTestResult("JsonAlternationLaw", jsonAlternationLaw)
      jsonAlternationLaw.passed === true
    }
  }
}
