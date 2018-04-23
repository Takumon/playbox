package org.stairwaybook.recipe

trait SimpleRecipes {
  this: SimpleFoods =>
  object FruitSalada extends Recipe(
    "fruit salad",
    List(Apple, Pear),
    "Mix it all together"
  )
  def allRecipes = List(FruitSalad)

}
