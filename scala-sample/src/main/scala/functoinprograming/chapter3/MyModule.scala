package functoinprograming.chapter3

import functoinprograming.chapter3.List._

object MyModule {
  def exec(args: Array[String]): Unit =  {

    val intList = List(1,2,3,4,5)
    val doubleList = List(1.0,2.0,3.0,4.0,5.0)
    println(List.sum(intList))
    println(List.sum2(intList))
    println(List.product(doubleList))
    println(List.product2(doubleList))

    val x = List(1, 2, 3, 4, 5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + sum(t)
      case _ => 101
    }

    println(x)

    println( List(1,2,3,4,5).setHead(10))

    println(List(1,2,3,4).drop(1))
    println(List(1,2,3,4).drop(2))
    println(List(1,2,3,4).drop(3))
    println(List(1,2,3,4).drop(4))

    println(List(1,2,3,4).tail)
    println(List(1,2,3,4).tail.tail)
    println(List(1,2,3,4).tail.tail.tail)
    println(List(1,2,3,4).tail.tail.tail.tail)
    println(List(1,2,3,4).tail.tail.tail.tail.tail)

    println(List(1,2,3,4).init)
    println(List(1,2,3,4).init.init)
    println(List(1,2,3,4).init.init.init)
    println(List(1,2,3,4).init.init.init.init)
    println(List(1,2,3,4).init.init.init.init.init)

    val xs = List(1,2,3,4)
    println(dropWhile(xs)(_ < 3))

    println(List.foldRight(List(1,2,3), Nil: List[Int])(Cons(_, _)))

    println(List.length2(List(5,5,5,5,5,  5,5,5,5,5,  5,5,5,5,5,  5,5,5,5,5)))
    println(List.length3(List(5,5,5,5,5,  5,5,5,5,5,  5,5,5,5,5,  5,5,5,5,5)))

    println(List.hasSubsequence(List(2, 3, 4, 3, 4, 3), List(3, 4, 3, 5)))

    println("reverse = " + List.reverse(List(1,2,3,4)))


    val branch = Branch(Branch(Leaf("A"), Leaf("B")) , Leaf("C"))
    println(Tree.size(branch))

    val branch2 = Branch(
        Branch(
          Leaf(3),
          Branch(
            Leaf(12),
            Branch(
              Branch(
                Leaf(2),
                Leaf(30)),
              Leaf(2)))) ,
        Leaf(3)
    )
    println(Tree.maximum(branch2))
    println(Tree.depth(branch2))

    val branch3 = Tree.map(branch2)(_ + "th")
    println(branch3)


    println(List.doubleToString(List(1.0, 2.2, 3.4)))

    val filttered = List.filterViaFlatMap(List("bob", "jon", "brond", "caccy", "big"))(_.contains("b"))
    println(filttered)

    val filttered2 = List.filterViaFlatMap(List(1, 2, 3, 4, 5))(_ % 2 == 0)
    println(filttered2)


    println( List.flatMap(List("bob", "jon", "brond", "caccy", "big"))(c => List(c, c)))

    val strList = List("one", "two", "three", "four", "five")
    val dList = List(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)

    println(List.zipWith(strList, dList)((s, d) => s + d.toString))

  }
}
