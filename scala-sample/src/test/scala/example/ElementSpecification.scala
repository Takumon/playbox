package example

import org.specs2.mutable.Specification
import Element.elem

class ElementSpecification extends Specification{
 "A UniformElement" should {
   "引数で渡した値が幅である" in {
      val ele = elem('x', 2, 3)
     ele.width must be_==(2)
   }
   "引数で渡した値が高さである" in {
     val ele = elem('x', 2, 3)
     ele.height must be_==(3)
   }
   "引数に負の値を指定すると例外が発生する" in {
     elem('x', -2, 3) must
      throwA[IllegalArgumentException]
   }
 }
}

