import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "render the index page" in new WithApplication{

      val result = route(FakeRequest(GET, "/")).get

      status(result) must equalTo(OK)
    }
  }
}
