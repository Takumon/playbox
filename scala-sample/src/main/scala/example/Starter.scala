package example

import Element.elem

object Starter extends App {

  val space = elem(" ")
  val corner = elem("+")

  def spiral(nEdge: Int, direction: Int): Element = {
    if (nEdge == 1)
      elem("+")
    else {
      val sp = spiral(nEdge - 1, (direction + 3) % 4)
      def verticalBar = elem('|', 1, sp.height)
      def horizontalBar = elem('-', sp.width, 1)
      direction  match {
        case 0 => (corner beside  horizontalBar)  above   (sp beside space)             // → を 上 に追加
        case 1 => (sp above space)                beside  (corner above verticalBar)    // ↓ を 右 に追加
        case 2 => (space beside sp)               above   (horizontalBar beside corner) // ← を 下 に追加
        case 3 => (verticalBar above corner)      beside  (space above sp)              // ↑ を 左 に追加
      }
    }
  }

  println(spiral(50, 0))


}

