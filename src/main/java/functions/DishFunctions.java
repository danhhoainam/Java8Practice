package functions;

import enums.CaloricLevel;
import models.Dish;

import java.util.function.Function;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class DishFunctions {

	public static Function<Dish, CaloricLevel> groupingByCaloricLevelFunction = dish -> {
		if (dish.getCalories() <= 400) {
			return CaloricLevel.DIET;
		} else if (dish.getCalories() <= 700) {
			return CaloricLevel.NORMAL;
		} else {
			return CaloricLevel.FAT;
		}
	};
}
