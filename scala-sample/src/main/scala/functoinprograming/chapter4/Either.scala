package functoinprograming.chapter4

import functoinprograming.chapter4.Option.{Try, map2}

sealed trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B] = this match {
    case Left(v) => Left(v)
    case Right(v) => Right(f(v))
  }
  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Left(v) => Left(v)
    case Right(v) => f(v)
  }
  def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
    case Left(v) => b
    case Right(v) => this
  }


  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    this flatMap{ (aa: A) =>
      b map { (bb: B) =>
        f(aa, bb)
      }
    }

  def map2_1[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    for {
      aa <- this
      bb <- b
    } yield f(aa, bb)

}

case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]



object Either {
  def mean(xs: IndexedSeq[Double]): Either[String, Double] =
    if (xs.isEmpty)
      Left("mean of empty list!")
    else
      Right(xs.sum / xs.length)

  def safeDiv(x: Int, y: Int): Either[Exception, Int] =
    try Right(x/ y)
    catch { case e: Exception => Left(e) }


  def Try[A](a: => A): Either[Exception, A] =
    try Right(a)
    catch {case e: Exception => Left(e)}



  def sequence[E, A](a: List[Either[E, A]]): Either[E, List[A]] =
    a.foldRight[Either[E, List[A]]](Right(Nil))( (x, acc) => x.map2(acc)(_ :: _))


  def traverse[E, A, B](a: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    a match {
      case Nil => Right(Nil)
      case h::t => f(h).map2(traverse(t)(f))(_ :: _)
    }

  def traverse_1[E, A, B](a: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    a.foldRight[Either[E, List[B]]](Right(Nil))( (x, acc) => f(x).map2(acc)(_ :: _))


  def insuranceRateQuote(age: Int, tickets: Int): Double = age / tickets

}