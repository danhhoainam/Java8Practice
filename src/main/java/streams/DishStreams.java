package streams;

import collectors.ToListCollector;
import enums.CaloricLevel;
import enums.DishType;
import models.Dish;

import java.util.*;
import java.util.stream.Collectors;

import static functions.DishFunctions.groupingByCaloricLevelFunction;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static predicates.DishPredicates.*;

/**
 * @author: Nichol
 * collect() -> terminal operation with Collector parameters
 * Collectors: filtering and flatMapping are introduced in Java 9
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class DishStreams {

	public static Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

	/**
	 * old style
	 * @param dishes
	 * @return
	 */
	public static List<String> getLowCaloDishesOldStyle(List<Dish> dishes) {
		List<Dish> lowCaloriesDishes = new ArrayList<>();

		for (Dish dish : dishes) {
			if (dish.getCalories() < 400) {
				lowCaloriesDishes.add(dish);
			}
		}

		Collections.sort(lowCaloriesDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return Integer.compare(o1.getCalories(), o2.getCalories());
			}
		});

		List<String> lowCaloriesDishNames = new ArrayList<>();
		for (Dish dish : lowCaloriesDishes) {
			lowCaloriesDishNames.add(dish.getName());
		}

		return lowCaloriesDishNames;
	}

	/**
	 * stream chaining
	 * @param dishes
	 * @return
	 */
	public static List<String> getLowCaloDishes(List<Dish> dishes) {

		return dishes.parallelStream()
				.filter(hasCaloriesLessThan400)
				.sorted(comparing(Dish::getCalories).reversed())
				.map(Dish::getName)
				.collect(toList());
	}

	/**
	 * filter example
	 * @param dishes
	 * @return
	 */
	public static List<Dish> getVegeterianDishes(List<Dish> dishes) {

		return dishes.stream()
				.filter(isVegeterian)
				.collect(Collectors.toList());
	}

	/**
	 * map example
	 * @param dishes
	 * @return
	 */
	public static List<Integer> getDishNamesLength(List<Dish> dishes) {

		return dishes.stream()
				.map(Dish::getName)
				.map(String::length)
				.collect(Collectors.toList());
	}

	/*********************************** GROUPING - START ***********************************/
	/**
	 * groupingBy use Dish Type to group data
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, List<Dish>> groupDishesByTypes(List<Dish> dishes) {

		return dishes.stream().collect(groupingBy(Dish::getType));
	}

	/**
	 * use calories number to group data
	 * {DIET=[chicken], NORMAL=[beef], FAT=[pork]}
	 * @param dishes
	 * @return
	 */
	public static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel(List<Dish> dishes) {

		return dishes.stream().collect(groupingBy(groupingByCaloricLevelFunction));
	}

	/**
	 * {MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
	 *  FISH={DIET=[prawns], NORMAL=[salmon]},
	 *  OTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Map<CaloricLevel, List<Dish>>> groupDishesByTypeAndCaloricLevel(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType,
						groupingBy(groupingByCaloricLevelFunction)));
	}

	/**
	 * Count in how many of each dish type
	 * {MEAT=3, FISH=2, OTHER=10}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Long> countEachTypeOfDishes(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType, counting()));
	}

	/**
	 * group by type
	 * then find the max in each group
	 * {FISH=Optional[salmon], OTHER=Optional[pizza], MEAT=Optional[pork]}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Optional<Dish>> findMaxCaloriesOfEachDishType(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
	}

	/**
	 * group by type
	 * then find the min in each group
	 * {FISH=Optional[salmon], OTHER=Optional[pizza], MEAT=Optional[pork]}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Optional<Dish>> findMinCaloriesOfEachDishType(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType, minBy(comparingInt(Dish::getCalories))));
	}

	/**
	 * group by type
	 * then find the max in each group
	 * {FISH=salmon, OTHER=pizza, MEAT=pork}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Dish> findMaxCaloriesOfEachDishTypeWithoutOptional(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType,
						collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
	}

	/**∂
	 * group by type
	 * then find the min in each group
	 * {FISH=salmon, OTHER=pizza, MEAT=pork}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Dish> findMinCaloriesOfEachDishTypeWithoutOptional(List<Dish> dishes) {

		return dishes.stream()
				.collect(groupingBy(Dish::getType,
						collectingAndThen(minBy(comparingInt(Dish::getCalories)), Optional::get)));
	}

	/**
	 * group by dish type
	 * then transfer each dish to caloric level
	 * put caloric level to set
	 * {OTHER=[DIET, NORMAL], MEAT=[DIET, NORMAL, FAT], FISH=[DIET, NORMAL]}
	 * @param dishes
	 * @return
	 */
	public static Map<DishType, Set<CaloricLevel>> groupCaloricLevelByDishType(List<Dish> dishes) {

		// with toCollection, we have more control for the output type
		return dishes.stream()
				.collect(groupingBy(Dish::getType, mapping(groupingByCaloricLevelFunction, toCollection(HashSet::new))));

//		return dishes.stream()
//				.collect(groupingBy(Dish::getType, mapping(groupingByCaloricLevelFunction, toSet())));
	}
	/*********************************** GROUPING - END ***********************************/


	/*********************************** PARTITIONING - START ***********************************/
	// Partitioning receives Predicate as the parameter
	// Divide data into 2 subset: true and false
	// partitioningBy forms:
	// form 1: partitioningBy(Predicate)
	// form 2: partitioningBy(Predicate, Collector)

	// collectingAndThen forms:
	// collectingAndThen(Collection, Function)

	/**
	 * divide dishes into 2 parts
	 * filter --> return 1 side, partitioning can keep both sides
	 * {false=[pork, beef, chicken, prawns, salmon],
	 *  true=[french fries, rice, season fruit, pizza]}
	 * @param dishes
	 * @return
	 */
	public static Map<Boolean, List<Dish>> partitionVegetarianDishes(List<Dish> dishes) {

		return dishes.stream().collect(partitioningBy(Dish::isVegetarian));
	}

	/**
	 * partitioning by vegetarian
	 * group each subset by type
	 * we got 2-level map
	 * {false={FISH=[prawns, salmon], MEAT=[pork, beef, chicken]},
	 *  true={OTHER=[french fries, rice, season fruit, pizza]}}
	 * @param dishes
	 * @return
	 */
	public static Map<Boolean, Map<DishType, List<Dish>>> partitionByVegeThenGroupByType(List<Dish> dishes) {

		return dishes.stream()
				.collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
	}

	/**
	 * partitioning by vegetarian
	 * then find max caloric dish in each group
	 * {false=pork, true=pizza}
	 * @param dishes
	 * @return
	 */
	public static Map<Boolean, Dish> partitionVegetarianThenFindMaxCaloricDish(List<Dish> dishes) {

		return dishes.stream()
				.collect(partitioningBy(Dish::isVegetarian,
						collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
	}

	/*********************************** PARTITIONING - END ***********************************/

	/**
	 * allMatch example
	 * @param dishes
	 * @param maxCalories
	 * @return
	 */
	public static boolean isHealthyMenu(List<Dish> dishes, int maxCalories) {

		return dishes.stream().allMatch(dish -> dish.getCalories() < maxCalories);
	}

	/**
	 * noneMatch
	 * @param dishes
	 * @return
	 */
	public static boolean isBadCaloriesMenu(List<Dish> dishes) {

		return dishes.stream().noneMatch(dish -> dish.getCalories() < 2000);
	}

	/**
	 * anyMatch example
	 * @param dishes
	 * @return
	 */
	public static boolean isVegetarianMenu(List<Dish> dishes) {

		return dishes.stream().anyMatch(Dish::isVegetarian);
	}

	/**
	 * find any vege dish
	 * @param dishes
	 * @return
	 */
	public static Optional<Dish> findAnyVegetarianDish(List<Dish> dishes) {

		return dishes.stream().filter(Dish::isVegetarian).findAny();
	}

	/**
	 * find first vege dish
	 * @param dishes
	 * @return
	 */
	public static Optional<Dish> findFirstVegetarianDish(List<Dish> dishes) {

		return dishes.stream().filter(Dish::isVegetarian).findFirst();
	}

	/**
	 * map - reduce model
	 * @param dishes
	 * @return
	 */
	public static int countDishes(List<Dish> dishes) {

		return dishes.stream().map(dish -> 1).reduce(0, (a, b) -> a + b);
	}

	/**
	 * use mapToInt intermediate operation to transform int stream
	 * find max value and return Optional
	 * orElse to return primitive values
	 * @param dishes
	 * @return
	 */
	public static int findMaxCalories(List<Dish> dishes) {

		return dishes.stream()
				.mapToInt(Dish::getCalories)
				.max()
				.orElse(1);
	}

	/**
	 * find max calories dishes by comparator
	 * use collector to group
	 * @param dishes
	 * @return
	 */
	public static Optional<Dish> findMaxCaloriesDishes(List<Dish> dishes) {

		return dishes.stream().collect(maxBy(dishCaloriesComparator));
	}

	/**
	 * reducing is the generic version of some function such as: maxBy, minBy, joining, summingInt
	 * @param dishes
	 * @return
	 */
	public static Optional<Dish> findMaxCaloriesDishesWithGenericVersion(List<Dish> dishes) {

		return dishes.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
	}

	/**
	 * find min calories dishes by comparator
	 * use collector to group
	 * @param dishes
	 * @return
	 */
	public static Optional<Dish> findMinCaloriesDishes(List<Dish> dishes) {

		return dishes.stream().collect(minBy(dishCaloriesComparator));
	}

	/**
	 * use mapToInt intermediate operation to transform int stream
	 * int stream support sum function
	 * @param dishes
	 * @return
	 */
	public static int getSumDishesCalories(List<Dish> dishes) {

		return dishes.stream()
				.mapToInt(Dish::getCalories)
				.sum();
	}

	/**
	 * use collector to sum the output values
	 * we can use different way with map and sum operations
	 * we have summingLong and summingDouble with the same mechanism
	 * @param dishes
	 * @return
	 */
	public static int summingIntDishesWithCollector(List<Dish> dishes) {

		return dishes.stream().collect(summingInt(Dish::getCalories));
	}

	/**
	 * Using reducing is the generic version of summingInt
	 * It has worse readable code than the solution above
	 * @param dishes
	 * @return
	 */
	public static int summingIntDishesWithGenericVersion(List<Dish> dishes) {

		return dishes.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
	}

	/**
	 * calculate the avg calories of dishes
	 * @param dishes
	 * @return
	 */
	public static double calculateAvgCaloriesOfDishes(List<Dish> dishes) {

		return dishes.stream().collect(averagingInt(Dish::getCalories));
	}

	/**
	 * IntSummaryStatistics contains count, sum, min, max, average
	 * @param dishes
	 * @return
	 */
	public static IntSummaryStatistics summarizingStatisticsOfDishes(List<Dish> dishes) {

		return dishes.stream().collect(summarizingInt(Dish::getCalories));
	}

	/**
	 * join all the dish names with white spaces between of each names
	 * @param dishes
	 * @return
	 */
	public static String getTheListOfDishNames(List<Dish> dishes) {

		return dishes.stream().map(Dish::getName).collect(joining());
	}

	/**
	 * join all the dish names with white spaces between of each names
	 * @param dishes
	 * @return
	 */
	public static String getTheListOfDishNamesWithCommas(List<Dish> dishes) {

		return dishes.stream().map(Dish::getName).collect(joining(", "));
	}

	/**
	 *
	 * @param dishes
	 * @return
	 */
	public static List<Dish> getAllDishesWithToListCollector(List<Dish> dishes) {

		return dishes.stream()
				.filter(Dish::isVegetarian)
				.collect(new ToListCollector<>());
	}
}
