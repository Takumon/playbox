object Closure {
  def main(args: Array[String]) {
    val candidates = List(1,2,3,4)

    val filter = (predicate: Int => Boolean) => {
      (candidates : List[Int]) => {
        for(x <- candidates; if predicate(x)) yield x
      }
    }

    val predicate = (x:Int) => x % 2 == 0

    var oddFilter = filter(predicate)

    oddFilter(candidates).foreach(println)
  }
}