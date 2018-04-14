package example

import Element.elem
import org.stairwaybook.expr._
import org.stairwaybook.simulation._

object Starter extends App {
  start()

  def start() = {

    val space = elem(" ")
    val corner = elem("+")

    def spiral(nEdge: Int, direction: Int): Element = {
      if (nEdge == 1)
        elem("+")
      else {
        val sp = spiral(nEdge - 1, (direction + 3) % 4)

        def verticalBar = elem('|', 1, sp.height)

        def horizontalBar = elem('-', sp.width, 1)

        direction match {
          case 0 => (corner beside horizontalBar) above (sp beside space) // → を 上 に追加
          case 1 => (sp above space) beside (corner above verticalBar) // ↓ を 右 に追加
          case 2 => (space beside sp) above (horizontalBar beside corner) // ← を 下 に追加
          case 3 => (verticalBar above corner) beside (space above sp) // ↑ を 左 に追加
        }
      }
    }

    println(spiral(50, 0))


    val f = new ExprFormatter
    val e1 = BinOp("*",
      BinOp("/", Number(1), Number(2)),
      BinOp("+", Var("x"), Number(1))
    )
    val e2 = BinOp("+",
      BinOp("/", Var("x"), Number(2)),
      BinOp("/", Number(1.5), Var("x"))
    )
    val e3 = BinOp("/", e1, e2)

    def show(e: Expr) = println(f.format(e) + "\n\n")

    for (e <- Array(e1, e2, e3)) show(e)

    import MySimulation._

    val input1, input2, sum, carry = new Wire
    probe("sum", sum)
    probe("carry", carry)
    halfAdder(input1, input2, sum, carry)
    input1 setSignal true
    run
    input2 setSignal true
    run
  }



  object MySimulation extends CircuitSimulation {
    override def InverterDelay: Int = 1

    override def AndGateDelay: Int = 3

    override def OrGateDeleay: Int = 5
  }
}

