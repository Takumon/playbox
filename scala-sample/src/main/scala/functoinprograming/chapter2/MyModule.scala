package functoinprograming.chapter2


object MyModule {
  def abs(n: Int): Int =
    if (n < 0) -n
    else n

  def factorial(n: Int): Int = {
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n -1, n * acc)

    go(n, 1)
  }

  def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d."
    msg.format(name, n, f(n))
  }

  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    @annotation.tailrec
    def loop(n: Int): Int =
      if (n >= as.length) -1
      else if(p(as(n))) n
      else loop(n + 1)

    loop(0)
  }

  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def loop(n: Int): Boolean =
      if (n >= as.length - 1) true
      else if (!ordered(as(n),as(n + 1))) false
      else loop(n + 1)

    loop(0)
  }

  def partiall[A, B, C](a: A, f: (A,B) => C): B => C =
    (b: B) => f(a, b)

  def curry[A, B, C](f: (A, B) => C): A => (B => C) =
    (a: A) => (b: B) => f(a, b)

  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a: A, b: B) => f(a)(b)

  def compose[A, B, C](f: B => C, g: A => B): A => C =
    (a: A) => f(g(a))

  def exec(args: Array[String]): Unit = {
    println(formatResult("absolute value", -42, abs))
    println(formatResult("factorial", 7, factorial))

    println(findFirst(Array(7,9,13), (x: Int) => x == 9))

    println(isSorted[Int](Array(1 ,2, 6, 4, 5), _ <= _))

    def add(a: Int, b: Int) = a + b
    def curryadd = curry(add)
    println(curryadd(1)(3))

    def radd = uncurry(curryadd)
    println(radd(4, 3))

    def fun1 = (a: Int) => a + 10
    def fun2 = (b: Int) => b * b
    def fun12 = compose(fun1, fun2)
    println(fun12(12))

    val f = (x: Double) => math.Pi / 2 - x
    val cos = f andThen math.sin
  }
}
