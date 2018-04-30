package functoinprograming.chapter6

object MyModule {
  def exec(args: Array[String]): Unit = {
    val rng = SimpleRNG(42)
    val ((i1, i2), rng2) = randomPair(rng)
    val (d1, rng3) = double(rng2)
    val (d2, rng4) = double(rng3)


    println(i1)
    println(i2)
    println(d2)
    println(d2)

    println(ints(10)(rng4)._1)




  }





}





trait Foo {
  def bar: (Bar, Foo)
  def baz: (Int, Foo)
}


class FooState
class Bar