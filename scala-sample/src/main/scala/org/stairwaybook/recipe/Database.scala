package org.stairwaybook.recipe

abstract class Database extends FoodCategories {
  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  def foodNamed(name: String): Option[Food] =
    allFoods.find(_.name == name)
}

