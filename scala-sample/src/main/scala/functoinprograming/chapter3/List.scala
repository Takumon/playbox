package functoinprograming.chapter3

sealed trait List[+A] {
  def init[U >: A](): List[U] = this match {
    case Nil => Nil
    case Cons(h, Nil) => Nil
    case Cons(h, t) => Cons(h, t.init)
  }


  def tail[U >: A](): List[U] = this match {
    case Nil => Nil
    case Cons(x, xs) => xs
  }

  def drop[U >: A](n: Int): List[U] = {
      this match {
        case Cons(_, t) if n > 0 => t.drop(n - 1)
        case _ => this
      }
  }

  def setHead[U >: A](h: U): List[U] = this match {
    case Nil => throw new IllegalArgumentException("Nilに対してsetHeadは実行できません")
    case Cons(x, xs) => Cons(h, xs)
  }
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, d: List[A]) extends List[A]

object List {

  def startWith[A](l: List[A], prefix: List[A]): Boolean = (l, prefix) match {
    case (_, Nil) => true
    case (Cons(h,t), Cons(h2, t2)) if h == h2 => startWith(t, t2)
    case _ => false
  }

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
    case Nil => sub == Nil
    case _ if startWith(sup, sub) => true
    case Cons(h, t) => hasSubsequence(t, sub) // 次の行を探す
  }

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def sum2(ints: List[Int]): Int =
    foldRight(ints, 0)(_ + _)

  def sum3(ints: List[Int]): Int =
    foldLeft(ints, 0)(_ + _)

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def product2(ds: List[Double]): Double =
    foldRight(ds, 1.0)(_ * _)


  def product3(ds: List[Double]): Double =
    foldLeft(ds, 1.0)(_ * _)

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  }

  def reverse[A](l: List[A]): List[A] = foldLeft(l, List[A]())( (acc, h) => Cons(h, acc) )

  def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(reverse(l), z)((b, a) => f(a, b))


  def foldLeftViaFoldRight[A, B](l: List[A], z: B)(f: (B, A) => B): B =
    foldRight(l, (b: B) => b)((a, g) => b => g(f(b, a)))(z)

  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f)
  }

  def length2[A](l : List[A]): Int =
    foldRight[A, Int](l, 0)((_, b) => b + 1)

  def length3[A](l : List[A]): Int =
    foldLeft(l, 0)((acc, h) => acc + 1)


  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(h, t) if f(h) => dropWhile(t)(f)
    case _ => l
  }

  def append[A](a1: List[A], a2: List[A]): List[A] = a1 match {
    case Nil => a2
    case Cons(h, t) => Cons(h, append(t, a2))
  }

  def add1(l: List[Int]): List[Int] =
    foldRight(l, Nil:List[Int])((h,t) => Cons(h+1, t))

  def add11(l: List[Int]): List[Int] =
    map(l)(_ + 1)

  def doubleToString(l: List[Double]): List[String] =
    foldRight(l, Nil: List[String])((h, t) => Cons(h.toString, t))

  def doubleToString2(l: List[Double]): List[String] =
    map(l)(_.toString)

  def map[A, B](l: List[A])(f: A => B) =
    foldRight(l, Nil: List[B])((h, t) => Cons(f(h), t))


  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] =
    foldRight(l, Nil: List[B])( (h,t) => List.append(f(h), t))

  def filter[A](as: List[A])(f: A => Boolean) =
    foldRight(as, Nil: List[A])((h, t) => if (f(h)) Cons(h, t) else t)


  def filterViaFlatMap[A](as: List[A])(f: A => Boolean) =
    flatMap(as)(a => if (f(a)) List(a) else Nil)

  def zipWith[A, B, C](a: List[A], b: List[B])(f: (A, B) => C): List[C] = (a, b) match {
    case (_, Nil) => Nil
    case (Nil, _) => Nil
    case (Cons(headA, tailA), Cons(headB, tailB)) => Cons(f(headA, headB), zipWith(tailA, tailB)(f))
  }


  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}


