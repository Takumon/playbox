package functoinprograming.chapter4

case class Person(name: Name, age: Age)
sealed class Name(val value: String)
sealed class Age(val value: Int)


object Person {

  def mkName(name: String): Either[String, Name] =
    if(name == "" || name == null) Left("Name is empty")
    else Right(new Name(name))

  def mkAge(age: Int): Either[String, Age] =
    if (age < 0) Left("Age is out of range")
    else Right(new Age(age))

  def mkPerson(name: String, age: Int): Either[String, Person] =
    (mkName(name), mkAge(age)) match {
      case (Left(msgName), Left(msgAge)) => Left(msgName + " " + msgAge)
      case (_, a @Left(_)) => a
      case (n @Left(_), _) => n
      case (Right(n), Right(a)) => Right(Person(n, a))
    }

}
