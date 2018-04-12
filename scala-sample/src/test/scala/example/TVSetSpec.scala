package example

import org.scalatest._

class TVSetSpec extends FeatureSpec with GivenWhenThen {
  feature("TV power button") {
    scenario("User presses power button when TV is off") {
      Given("a TV set that is switched off")
      When("the power button i s pressed")
      Then("the TV should switch on")
      pending
    }
  }
}
