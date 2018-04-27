package functoinprograming.chapter4

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }

  def getOrElse[B >: A](default: => B):B = this match {
    case None => default
    case Some(a) => a
  }

  def flatMap[B](f: A => Option[B]): Option[B] =
    map(f) getOrElse None // 一回そうOptionを剥がしてあげる

  def flatMap_1[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(a) => f(a)
  }

  def orElse[B>:A](obj: => Option[B]): Option[B] =
    this map (Some(_)) getOrElse obj

  def orElse_1[B >: A](obj: => Option[B]): Option[B] = this match {
    case None => obj
    case _ => this
  }

  def filter(f: A => Boolean): Option[A] =
    flatMap((a:A) => if (f(a)) Some(a) else None)

  def filter_1(f: A => Boolean): Option[A] = this match {
    case Some(a) if f(a) => Some(a)
    case _ => None
  }

}

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def variance(xs: Seq[Double]): Option[Double] =
    mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))


  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  // 引数は遅延評価
  def Try[A](a: => A): Option[A] =
    try Some(a)
    catch { case e: Exception => None }


  def insuranceRateQuote(age: Int, tickets: Int): Double = age / tickets

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = (a, b) match {
    case (Some(valueA), Some(valueB)) => Some(f(valueA, valueB))
    case _ => None
  }

  def map2_1[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C) =
    a.flatMap { aa =>
      b map { bb=>
        f(aa,bb)
      }
    }

  def map2_2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C) =
    for {
      aa <- a
      bb <- b
    } yield f(aa, bb)


  def sequence[A](a: List[Option[A]]): Option[List[A]] =
    a.foldRight[Option[List[A]]](Some(Nil))( (x, acc) => map2(x, acc)(_ :: _))

  def parseInt(a: List[String]): Option[List[Int]] =
    traverse(a)(a => Try(a.toInt))

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a match {
      case Nil => Some(Nil)
      case h::t => map2(f(h), traverse(t)(f))(_ :: _)
    }

  def traverse_1[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a.foldRight[Option[List[B]]](Some(Nil))( (x, acc) => map2(f(x), acc)(_ :: _))
}