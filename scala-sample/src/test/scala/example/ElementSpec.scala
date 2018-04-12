package example

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import Element.elem

class ElementSpec extends FlatSpec with Matchers {
  "UniformElement" should
    "widthは引数で指定した値" in {
    val ele = elem('x', 2, 3)
    ele.width should be (2)
  }

  it should "have a height equal to the pass value" in {
    val ele = elem('x', 2, 3)
    ele.height should be (3)
  }

  it should "throw an IAE if passed a negative width" in {
    an[IllegalArgumentException] should be thrownBy {
      throw new IllegalArgumentException()
    }
  }

}
