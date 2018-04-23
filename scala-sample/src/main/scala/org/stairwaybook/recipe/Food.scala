package org.stairwaybook.recipe

abstract class Food(val name: String) {
  override def toString: String = name
}

class Recipe (
  val name: String, // 名前
  val ingredients: List[Food], // 材料リスト
  val instructions: String // 作り方
) {
  override def toString: String = name
}

object Apple extends Food("Apple")
object Orange extends Food("Orange")
object Cream extends Food("Cream")
object Sugar extends Food("Sugar")

object FruitSalad extends Recipe(
  "fruit salad",
  List(Apple, Orange, Cream, Sugar),
  "Stir it all together."
)
