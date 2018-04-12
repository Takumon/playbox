package org.stairwaybook.expr

import example.Element
import example.Element.elem

sealed abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
class ExprFormatter {
  private val opGroups =
    Array(
      Set("|", "||"),
      Set("&", "&&"),
      Set("^"),
      Set("==", "!="),
      Set("<", "<=", ">", ">="),
      Set("+", "-"),
      Set("*", "%")
    )
  private val precedence = {
    val assocs =
      for {
        i <- 0 until opGroups.length
        op <- opGroups(i)
      } yield op -> i
    assocs.toMap
  }
  private val unaryPrecedince = opGroups.length
  private val fractionPrecedence =  -1

  def format(e: Expr, enclPrec: Int = fractionPrecedence): Element =
    e match {
      case Var(name) =>
        elem(name)
      case Number(num) =>
        def stripDop(s: String) =
          if (s endsWith ".0") s.substring(0, s.length - 2)
          else s
        elem(stripDop(num.toString))
      case UnOp(op, arg) =>
        elem(op) beside format(arg, unaryPrecedince)
      case BinOp("/", left, rigth) =>
        val top = format(left, fractionPrecedence)
        val bot = format(rigth, fractionPrecedence)
        val line = elem('-', top.width max bot.width, 1)
        val frac = top above line above bot
        if (enclPrec != fractionPrecedence) frac
        else elem(" ") beside frac beside elem(" ")
      case BinOp(op, left, rigth) =>
        val opPrec = precedence(op)
        val l = format(left, opPrec)
        val r = format(rigth, opPrec + 1)
        val oper = l beside elem(" " + op + " ") beside r
        if (enclPrec <= opPrec) oper
        else elem("(") beside oper beside elem(")")
    }
}
