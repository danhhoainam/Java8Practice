package predicates;

import models.Dish;

import java.util.function.Predicate;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class DishPredicates {

	public static Predicate<Dish> hasCaloriesLessThan400 = dish -> dish.getCalories() < 400;

	public static Predicate<Dish> isVegeterian = dish -> dish.isVegetarian();
}
