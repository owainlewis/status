import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    def getStatus(url: String) =
      status(route(FakeRequest(GET, url)).get)

    "render the index page" in new WithApplication {
      getStatus("/") must equalTo(OK)
    }

    "render the login page" in new WithApplication {
      getStatus("/login") must equalTo(OK)
    }

    "return not found when incident doesn't exist" in new WithApplication {
      // getStatus("/incidents/1") must equalTo(NOT_FOUND)
    }

    "return redirect when accessing private endpoints" in new WithApplication {

    }
  }
}
