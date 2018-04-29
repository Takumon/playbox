package functoinprograming.chapter5


object MyModule {
  def exec(args: Array[String]): Unit = {
    forStream()
  }

  def forStream(): Unit = {
    import functoinprograming.chapter5.Stream
    import functoinprograming.chapter5.Stream._

    val stream1 = Stream(
      "hogze",
      "fugaz",
      "zruru",
      "ruzna",
      "foucez"
    )


    println("stream1.forAll", stream1.forAll(_.contains("z")))
    println("stream1.exists", stream1.exists(_.contains("a")))

    val result1 = Stream(1,2,3,4).map(_ +10).filter(_ % 2 == 0).toList
    // ↓Streamの先頭が評価される
    val result2 = cons(11, Stream(2,3,4).map(_ + 10)).filter(_ % 2 == 0).toList
    // ↓filter判定（却下）
    val result3 = Stream(2,3,4).map(_ + 10).filter(_ % 2 == 0).toList
    // ↓Streamの先頭が評価される
    val result4 = cons(12, Stream(3,4).map(_ + 10)).filter(_ % 2 == 0).toList
    // ↓filter判定（許可）
    val result5 = 12 :: Stream(3, 4).map(_ + 10).filter(_ % 2 == 0).toList
    // ↓Streamの先頭を評価
    val result6 = 12 :: cons(13, Stream(4).map(_ + 10)).filter(_ % 2 == 0).toList
    // ↓filter判定(却下)
    val result7 = 12 :: Stream(4).map(_ + 10).filter(_ % 2 == 0).toList
    // ↓Steramの先頭を評価
    val result8 = 12 :: cons(14, Stream[Int]().map(_ + 10)).filter(_ % 2 == 0).toList
    // ↓filter判定(許可)
    val result9 = 12 :: 14 :: Stream[Int]().map(_ + 10).filter(_ % 2 == 0).toList
    // ↓Streamはからなので空リストを返す
    val result10 = 12 :: 14 :: List()


    val list1 = ones.take(5).toList
    println(list1)

    // ↓StackOverflowErrorになる
    // println(ones.exists(_ % 2 == 0))
    println(ones.map(_ + 1).exists(_ % 2 == 0))

    println(from(3).take(10).toList)
    println(from_1(3).take(10).toList)
    println(constant(4).take(3).toList)
    println(constant_1(4).take(3).toList)
    println(fibs().take(20).toList)
    println(fibs_1().take(20).toList)


    val s1 = Stream(1,3,4,5,6,7)
    val s1Start = Stream(1,3,4)
    val s1Subseuence = Stream(4,5)
    val s2 = Stream(1.2, 3.4, 3.3)
    println("zipWith", s1.zipWith(s2)( (a: Int, b: Double) => (b * a).toString).toList)

    println("zipAll", s1.zipAll(s2).toList)
    println("zipAll", s2.zipAll(s1).toList)
    println("startsWith", s1.startsWith(s1Start))
    println("hasSubsequence", s1.hasSubsequence(s1Subseuence))
    println("tails", s1.tails().toList.map(_.toList))
  }


  def maybeTwice(b: Boolean, i: => Int): Int = {
    lazy val j = i
    if (b) j + j
    else 0
  }

  def forFirst(): Unit = {
    val result1 = List(1,2,3,4).map(_ + 10).filter(_ % 2 == 0).map(_ * 3)
    val result2 = List(11,12,13,14).filter(_ % 2 == 0).map(_ * 3)
    val result3 = List(12, 14).map(_ * 3)
    val result4 = List(36, 42)

    println(
      result1,
      result2,
      result3,
      result4
    )
  }
}
