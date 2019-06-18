package bootstrap;

import enums.DishType;
import models.Dish;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class DishBootstrap {

	public static final List<Dish> menu = Arrays.asList(
			new Dish("pork", false, 800, DishType.MEAT),
			new Dish("beef", false, 700, DishType.MEAT),
			new Dish("chicken", false, 400, DishType.MEAT),
			new Dish("french fries", true, 530, DishType.OTHER),
			new Dish("rice", true, 350, DishType.OTHER),
			new Dish("season fruit", true, 120, DishType.OTHER),
			new Dish("pizza", true, 550, DishType.OTHER),
			new Dish("prawns", false, 300, DishType.FISH),
			new Dish("salmon", false, 450, DishType.FISH) );

	public static final Map<String, List<String>> dishTags = new HashMap<>();
	static {
		dishTags.put("pork", asList("greasy", "salty"));
		dishTags.put("beef", asList("salty", "roasted"));
		dishTags.put("chicken", asList("fried", "crisp"));
		dishTags.put("french fries", asList("greasy", "fried"));
		dishTags.put("rice", asList("light", "natural"));
		dishTags.put("season fruit", asList("fresh", "natural"));
		dishTags.put("pizza", asList("tasty", "salty"));
		dishTags.put("prawns", asList("tasty", "roasted"));
		dishTags.put("salmon", asList("delicious", "fresh"));
	}
}
