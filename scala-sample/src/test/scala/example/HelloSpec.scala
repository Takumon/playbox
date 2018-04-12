package example

import org.scalatest._
import Element.elem


class HelloSpec extends FunSuite {
  test("elem result should have passed width") {
    val ele = elem('x', 2, 3)
    assert(ele.width == 2)
  }

  test("elem result should have passed hight") {
    val ele = elem('x', 2, 3)
    assertResult(3) {
      ele.height
    }
  }

  test("should throw Exception") {
    val caught = intercept[ArithmeticException] {
      1 / 0
    }

    assert(caught.getMessage == "/ by zero")
  }
}
