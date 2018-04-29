package functoinprograming.chapter4



object MyModule {
  def maim(args: Array[String]): Unit = {
    forPerson()
  }

  def forPerson(): Unit = {

    val p1 = Person.mkPerson("Bob", 12)
    val p2 = Person.mkPerson(null, 12)
    val p3 = Person.mkPerson(null, -1)

    println(p1)
    println(p2)
    println(p3)

  }

  def forOption(): Unit = {
    import functoinprograming.chapter4.Option._

    val a = Some[String]("hoge")
    val b = Some[String]("fuga")
    val c = None

    val ab = for {
      aa <- a
      bb <- b
    } yield aa + bb

    val abc = for {
      aa <- a
      bb <- b
      cc <- c
    } yield aa + bb + cc

    println(ab)
    println(abc)


    def parseInsuranceRateQuote(
      age: String,
      numberOfSpeedingTickets: String
    ): Option[Double] = {
      val optAge: Option[Int] = Try { age.toInt }
      val optTickets: Option[Int] = Try { numberOfSpeedingTickets.toInt }

      map2(optAge, optTickets)(insuranceRateQuote)
    }

  }

  def forEither(): Unit = {
    import functoinprograming.chapter4.Either._

    val eitherA = Right("valueA")
    val eitherB = Right("valueB")
    val eatherC = Left("Wong String value")

    def plus = (a: String, b: String) => a + b

    println("either map = " + eitherA.map(_ + "plus"))
    println("either map2 = " + eitherA.map2(eitherB)(plus))
    println("either map2 = " + eitherA.map2(eatherC)(plus))


    def parseInsuranceRateQuote(
                                 age: String,
                                 numberOfSpeedingTickets: String
                               ): Either[Exception, Double] = {
      for {
        age <- Try { age.toInt }
        ticket <- Try { numberOfSpeedingTickets.toInt }
      } yield insuranceRateQuote(age, ticket)
    }


    println("parseInsuranceRateQuote = " + parseInsuranceRateQuote("48", "12"))
    println("parseInsuranceRateQuote = " + parseInsuranceRateQuote("hoge", "12"))
  }
}
