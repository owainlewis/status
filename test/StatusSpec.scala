import model.Investigating
import model.Status._
import org.scalacheck.{Test, Gen}
import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.specs2.mutable.Specification
import org.scalacheck.Test._

class StatusSpec extends Specification with BasicConsoleReporter {

  private val statusesNames = allStatuses.map(_.name)
  private val statusesInts = allStatuses.map(_.intValue)
  private val statusGen = Gen.oneOf(allStatuses)
  private val statusStringGen = Gen.oneOf(alphaStr, Gen.oneOf(statusesNames))

  "Every Status" should {

    "be convertable from string to object" in {
      val fromStringToObjectLaw = check(Test.Parameters.default,
        forAll(statusStringGen)(statusStr => allStatuses.contains(fromString(statusStr).getOrElse(Investigating))))
      reporter.onTestResult("FromStringToObject",fromStringToObjectLaw)
      fromStringToObjectLaw.passed === true
    }

    "be convertable from int to object" in {
      val fromIntToObjectLaw = check(Test.Parameters.default,
        forAll(frequency((1, Gen.oneOf(statusesInts)), (2, Int.MaxValue)))(int => allStatuses.contains(fromInt(int).getOrElse(Investigating))))
      reporter.onTestResult("FromIntToObject", fromIntToObjectLaw)
      fromIntToObjectLaw.passed === true
    }

    "have unique intValue" in {
      val uniqueIntValueLaw = check(Test.Parameters.default,
        forAll(statusGen)(status => statusesInts.count(int => status.intValue == int) == 1))
      reporter.onTestResult("UniqueIntValueLaw", uniqueIntValueLaw)
      uniqueIntValueLaw.passed === true
    }

    "have unique name" in {
      val uniqueNameLaw = check(Test.Parameters.default,
        forAll(statusGen)(status => statusesNames.count(name => status.name == name) == 1))
      reporter.onTestResult("UniqueNameLaw", uniqueNameLaw)
      uniqueNameLaw.passed === true
    }

    "have intValue increased by one to previous Status" in {
      statusesInts.sorted must_=== (1 to statusesInts.last).toList
    }
  }
}
