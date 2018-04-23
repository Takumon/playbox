package example

import Element.elem
import org.stairwaybook.expr._
import org.stairwaybook.simulation._
import org.stairwaybook.currency._
import scala.xml.Elem



object Starter {


//  drawSpiral()
//  drawExpression()
//  simulate()
//  currency()
//  swing()
  hoge()


  private def drawSpiral() = {

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
  }

  private def drawExpression() = {


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

  }

  private def simulate() = {
    object MySimulation extends CircuitSimulation {
      override def InverterDelay: Int = 1

      override def AndGateDelay: Int = 3

      override def OrGateDeleay: Int = 5
    }

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

  private def currency() = {
    val yen = Japan.Yen from US.Dollar * 100
    println(yen)
    val euro = Europe.Euro from yen
    println(euro)
    val dollar = US.Dollar from euro
    println(dollar)

    val dollar2 = US.Dollar * 100 + dollar
    println(dollar2)
  }

  private def swing() = {
    import java.awt.event.ActionEvent
    import java.awt.event.ActionListener
    import javax.swing.JButton

    implicit def function2ActionListener(f: ActionEvent => Unit) =
      new ActionListener {
        override def actionPerformed(event: ActionEvent): Unit = f(event)
      }


    val button = new JButton()
    button.addActionListener(
        (_: ActionEvent) => println("pressed!")
    )
  }

  private def hoge() = {
    import scala.collection.mutable

    val p1, p2 = new Point(1,2)
    val q = new Point(2,3)

    println(p1.hashCode())
    println(p2.hashCode())


  }





  def queens(n: Int): List[List[(Int, Int)]] = {
    def placeQueens(k: Int): List[List[(Int, Int)]] =
      if (k == 0)
        List(List())
      else
        for {
          queens <- placeQueens(k -1)
          column <- 1 to n
          queen = (k, column)
          if isSafe(queen, queens)
        } yield queen :: queens

    def isSafe(queen: (Int, Int), queens: List[(Int, Int)]): Boolean =
      queens forall (q => !inCheck(queen, q))

    def inCheck(q1: (Int, Int), q2: (Int, Int)) =
      q1._1 == q2._1 || // 同じ段
        q1._2 == q2._2 || // 同じ行
        (q1._1 - q2._1).abs == (q1._2 - q2._2).abs // 対角線上


    placeQueens(n)
  }

}

class Point(val x: Int, val y: Int) {
  override def equals(other: Any): Boolean = other match {
    case that: Point => this.x == that.x && this.y == that.y
    case _ => false
  }

  override def hashCode(): Int = (x, y).##
}


object Color extends Enumeration {
  val Red, Orange, Yello = Value
}


class ColorPoint(x: Int, y: Int, val color: Color.Value) extends Point(x, y) {
  override def equals(other: Any): Boolean = other match {
    case that: ColorPoint =>
        this.color == that.color && super.equals(that)
    case that: Point =>
        that equals this
    case _ => false
  }
}


import java.io._

class Reader(fname: String) {
  private val in =
    new BufferedReader(new FileReader(fname))

  @throws(classOf[IOException])
  def read() = in.read()
}