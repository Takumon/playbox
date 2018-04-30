package functoinprograming.chapter6



trait RNG {

  def nextInt: (Int, RNG)
}

case class SimpleRNG(seed: Long) extends RNG {
  def nextInt: (Int, RNG) = {
    val newSeed: Long = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n =(newSeed >>> 16).toInt
    (n, nextRNG)
  }
}

object PNG {
  type Rand[+A] = RNG => (A, RNG)

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def randomPair(rng: RNG): ((Int, Int), RNG) = {
    val (i1,rng2) = rng.nextInt
    val (i2,rng3) = rng2.nextInt
    ((i1,i2), rng3)
  }

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (i, r) = rng.nextInt
    (if (i < 0) -(i + 1) else i, r)
  }


  def nonNegativeIntViaMap(rng: RNG): (Int, RNG) =
    map(r => r.nextInt)(i => if (i < 0) -(i + 1) else i)(rng)
  

  def nonNegativeEven(rng: RNG): Rand[Int] =
    map(nonNegativeInt)(i => i - i % 2)

  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (i, rng2) = rng.nextInt
    val (d, rng3) = double(rng2)
    ((i ,d), rng3)
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val (d, rng2) = double(rng)
    val (i, rng3) = rng.nextInt
    ((d, i), rng3)
  }

  def double3(rng: RNG): ((Double, Double, Double), RNG) = {
    val (d1, rng2) = double(rng)
    val (d2, rng3) = double(rng2)
    val (d3, rng4) = double(rng3)
    ((d1, d2, d3), rng4)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    var currentRng = rng
    val list = (0 until count).map {i =>
      val (i, r) = currentRng.nextInt
      currentRng = r
      i
    } toList

    (list, currentRng)
  }



  def double(rng: RNG): (Double, RNG) = {
    val (i, r) = nonNegativeInt(rng)
    ( i / (Int.MaxValue + 1), r)
  }

  def doubleViaMap(rng: RNG): (Double, RNG) =
    map(nonNegativeInt)(i => i.toDouble / (Int.MaxValue + 1))(rng)
}