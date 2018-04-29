package functoinprograming.chapter5

import functoinprograming.chapter5.Stream._

trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  def headOption_1: Option[A] =
    foldRight(None: Option[A])((h, _) => Some(h))

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
    case _ => empty
  }

  def drop(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 0 => t().drop(n - 1)
    case _ => this
  }


  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) => if (p(h())) cons(h(), t().takeWhile(p)) else t().takeWhile(p)
    case _ => this
  }

  def takeWhileViaFoldRight(f: A => Boolean): Stream[A] =
    foldRight(empty[A])((h,t) =>
      if (f(h)) cons(h,t)
      else      empty)


  def takeViaUnfold(n: Int): Stream[A] =
    unfold((this,n)) {
      case (Cons(h,t), 1) => Some((h(), (empty, 0)))
      case (Cons(h,t), n) if n > 1 => Some((h(), (t(), n-1)))
      case _ => None
    }

  def toList: List[A] = this match {
    case Empty => List.empty
    case Cons(h, t) => h() :: t().toList
  }

  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h,t) => p(h()) || t().exists(p)
    case _ => false
  }

  def exists_1(p: A => Boolean): Boolean =
    foldRight(false)((h, t) => p(h) || t)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((h, t) => p(h) && t)

  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h,t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }


  def map[B](f: (A) => B): Stream[B] =
    foldRight(empty[B])((h, t) => cons(f(h), t))

  def mapViaUnfold[B](f: A => B): Stream[B] =
    unfold(this) {
      case Cons(h,t) => Some((f(h()), t()))
      case _ => None
    }

  def append[B>:A](s: => Stream[B]): Stream[B] =
    foldRight(s)((h, t) => cons(h, t))

  def flatMap[B](f: (A) => Stream[B]): Stream[B] =
    foldRight(empty[B])((h,t) => f(h).append(t))

  def filter[B>:A](f: (B) => Boolean): Stream[B] =
    foldRight(empty[B])((h,t) => if (f(h)) cons(h, t) else t)


//  def zipWith[A, B, C](a: List[A], b: List[B])(f: (A, B) => C): List[C] = (a, b) match {
//    case (_, Nil) => Nil
//    case (Nil, _) => Nil
//    case (Cons(headA, tailA), Cons(headB, tailB)) => Cons(f(headA, headB), zipWith(tailA, tailB)(f))
//  }

  def zipWith[B, C](s2: Stream[B])(f: (A,B) => C): Stream[C] =
    unfold( (this, s2) ) {
      case (Cons(h1, t1), Cons(h2, t2)) => Some( (f(h1(), h2()), (t1(), t2())) )
      case _ => None
    }

  def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])] =
    unfold( (this, s2) ) {
      case (Cons(h1, t1), Cons(h2, t2)) => {
        val value = (Some(h1()), Some(h2()))
        val next = (t1(), t2())

        Some( (value ,  next) )
      }
      case (a @Empty, Cons(h2, t2)) => {
        val value = ( None, Some(h2()))
        val next = (empty, t2())

        Some( (value ,  next) )
      }
      case (Cons(h1, t1), b @Empty) => {
        val value = (Some(h1()), None)
        val next = (t1(), empty)

        Some( (value ,  next) )
      }
      case _ => None
    }

  def startsWith[A](s: Stream[A]): Boolean =
    zipAll(s).takeWhile(!_._2.isEmpty) forAll {
      case (Some(a1), Some(a2)) => a1 == a2
      case _ => false
    }

  def tails(): Stream[Stream[A]] =
    unfold(this) {
      case Empty => None
      case s => Some( ( s, s drop 1))
    } append  Stream(empty)


  def hasSubsequence[A](s: Stream[A]): Boolean =
    tails().exists(_ startsWith s)


  def scanRight[B](z: B)(f: (A, B) => B): Stream[B] =
    foldRight((z, Stream(z)))((a, p0) => {
      lazy val p1 = p0
      val b2 = f(a, p1._1)
      (b2, cons(b2, p1._2))
    })._2

}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  // 無限ストリーム
  val ones: Stream[Int] = Stream.cons(1, ones)

  val ones_1: Stream[Int] =
    unfold(1)(s => Some((1, 1)))

  def constant[A](a: A): Stream[A] = Stream.cons(a, constant(a))

  def constant_1[A](a: A): Stream[A] =
    unfold(a)(s => Some((a, a)))

  def from(n: Int): Stream[Int] = Stream.cons(n, from(n + 1))

  def from_1(n: Int): Stream[Int] =
    unfold(n)(s => Some((n, n + 1)))

  def fibs(n: Int = 0, n1: Int = 1): Stream[Int] = Stream.cons(n, fibs(n1, n + n1))

  def fibs_1(n: Int = 0, n1: Int = 1): Stream[Int] =
    unfold((n, n1)) { s =>
      val a = s._1
      val nextS = (s._2, s._1 + s._2)
      Some((a, nextS))
    }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] =
    f(z) match {
      case Some((h,s)) => cons(h, unfold(s)(f))
      case None => empty
    }

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty
  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))
}
