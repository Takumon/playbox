package example

import Element.elem

/**
  * Elementのファクトリー
  */
object Element {
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(s: String): Element =
    new LineElement(s)

  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)


  /**
    * 複数行、複数列を想定したElement
    *
    * @param contents
    */
  private class ArrayElement(val contents: Array[String]) extends Element


  /**
    * 単一行、複数列を想定したElement
    *
    * @param s
    */
  private class LineElement(s: String) extends Element {
    override val contents = Array(s)
    override def width = s.length
    override def height = 1
  }

  /**
    * 1種類の文字で単一行、複数列を想定したElement
    *
    * @param chr
    * @param width
    * @param height
    */
  private class UniformElement (
     chr: Char,
     override val width: Int,
     override val height: Int
   ) extends Element {
    private val line = chr.toString * width
    override def contents: Array[String] = Array.fill(height)(line)
  }
}


/**
  * Elementの親クラス
  */
abstract class Element {
  def contents: Array[String]
  def height: Int = contents.length
  def width: Int = contents(0).length
  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width

    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for (
        (line1, line2) <- this1.contents zip that1.contents
      ) yield line1 + line2
    )
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }


  override def toString: String = contents mkString "\n"
}

