package org.stairwaybook.currency


abstract class CurrencyZone {
  type Currency <: AbstractCurrency

  val CurrencyUnit: Currency

  def make(x: Long): Currency

  abstract class AbstractCurrency {

    val amount: Long

    def designation: String

    def +(that: Currency): Currency =
      make(this.amount + that.amount)

    def -(that: Currency): Currency =
      make(this.amount - that.amount)

    def *(x: Double): Currency =
      make((this.amount * x).toLong)

    def /(x: Double): Currency =
      make((this.amount / x).toLong)

    def /(that: Currency): Currency =
      this / that.amount

    def from(other: CurrencyZone#AbstractCurrency): Currency = {
      val rate = Converter.exchangeRate(other.designation)(this.designation)
      make(math.round(other.amount.toDouble * rate))
    }

    override def toString: String = {
      val format = s"%.${decimals(CurrencyUnit.amount)}f $designation"
      val calculated = amount.toDouble / CurrencyUnit.amount.toDouble
      calculated formatted format
    }

    private def decimals(n: Long): Int =
      if (n == 1) 0 else 1 + decimals(n / 10)
  }

}

object Converter {
  var exchangeRate = Map(
    "USD" -> Map(
      "USD" -> 1.0,
      "EUR" -> 0.7596,
      "JPY" -> 1.211,
      "CHF" -> 1.223
    ),
    "EUR" -> Map(
      "USD" -> 1.316,
      "EUR" -> 1.0,
      "JPY" -> 1.594,
      "CHF" -> 1.623
    ),
    "JPY" -> Map(
      "USD" -> 0.8108,
      "EUR" -> 0.6272,
      "JPY" -> 1.0,
      "CHF" -> 1.018
    ),
    "CHF" -> Map(
      "USD" -> 0.8108,
      "EUR" -> 0.6160,
      "JPY" -> 0.982,
      "CHF" -> 1.0
    )
  )
}

object Europe extends CurrencyZone {

  abstract class Euro extends AbstractCurrency {
    override def designation: String = "EUR"
  }

  override type Currency = Euro

  def make(cents: Long) = new Euro {
    override val amount: Long = cents
  }

  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}

object Japan extends CurrencyZone {

  abstract class Yen extends AbstractCurrency {
    override def designation: String = "JPY"
  }

  override type Currency = Yen

  def make(yen: Long) = new Yen {
    override val amount: Long = yen
  }

  val Yen = make(1)
  val CurrencyUnit = Yen
}


object US extends CurrencyZone {

  abstract class Dollar extends AbstractCurrency {
    override def designation: String = "USD"
  }

  override type Currency = Dollar

  override def make(x: Long): Dollar = new Dollar {
    val amount = x
  }

  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}

