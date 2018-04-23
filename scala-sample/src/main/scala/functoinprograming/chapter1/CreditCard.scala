package functoinprograming.chapter1

class CreditCard(var store: Int) {
  def change(price: Int) = {
    store -= price
  }
}
