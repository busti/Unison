import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneServerPerTest

/**
  * Add your integration spec here.
  * An integration test will fire up a whole play application in a real (or headless) browser.
  */
class IntegrationSpec extends PlaySpec with GuiceOneServerPerTest with OneBrowserPerTest with HtmlUnitFactory {

  "Application" should {
    "work from within a browser" in {
      go to ("http://localhost:" + port)
    }
  }
}