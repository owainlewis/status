import java.util.Date

import model.Update
import org.scalacheck.{Prop, Test, Gen}
import org.specs2.mutable.Specification
import play.api.libs.json.Json

class UpdateSpec extends Specification with BasicConsoleReporter {

  val updateGen = for {
    id <- Gen.option(Gen.chooseNum[Long](0, Long.MaxValue))
    incident <- Gen.chooseNum[Long](0, Long.MaxValue)
    title <- Gen.alphaStr
    desc <- Gen.option(Gen.alphaStr)
    created <- Gen.chooseNum[Long](0, Long.MaxValue).map(new Date(_))
  } yield Update(id, incident, title, desc, created)

    "Update" should {

      "be alternate between his json and object form" in {
        val jsonAlternationLaw =
          Test.check(Test.Parameters.default, Prop.forAll(updateGen)(update => Json.toJson(update).as[Update] == update))
        reporter.onTestResult("JsonAlternationLaw", jsonAlternationLaw)
        jsonAlternationLaw.passed === true
      }
    }
}
