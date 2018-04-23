package org.stairwaybook.recipe

object StudentDatabase extends Database {
  object FrozenFood extends Food("FrozenFood")
  object HealItUp extends Recipe (
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minites.")

  override def allFoods: List[Food] = List(FrozenFood)

  override def allRecipes: List[Recipe] = List(HealItUp)

  override def allCategories: List[FoodCategory] = List(
    FoodCategory("edible", List(FrozenFood))
  )
}
