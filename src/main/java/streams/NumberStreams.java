package streams;

import collectors.PrimeNumbersCollector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.partitioningBy;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class NumberStreams {

	/**
	 * find the even number
	 * get the distinct values
	 * collect the data into List of Integer
	 * @param numbers
	 * @return
	 */
	public static List<Integer> filterEvenUniqueInteger(List<Integer> numbers) {

		return numbers.stream()
				.filter(number -> number % 2 == 0)
				.distinct()
				.collect(Collectors.toList());
	}

	public static List<Integer> squareNumbers(List<Integer> numbers) {

		return numbers.stream()
				.map(number -> number * number)
				.collect(Collectors.toList());
	}

	/**
	 * [1, 3, 4] + [5, 8] = [(1, 5), (3, 5), (4, 5), (1, 8), (3, 8), (4, 8)]
	 * @param numbers
	 * @param combination
	 * @return
	 */
	public static List<int[]> listToArrayOfPairs(List<Integer> numbers, List<Integer> combination) {

		return numbers.stream()
				.flatMap(number -> combination.stream()
						.map(com -> new int[]{ number, com })
				)
				.collect(Collectors.toList());
	}

	/**
	 * [1, 3, 4] + [5, 8] = [(1, 5), (4, 5), (1, 8), (4, 8)]
	 * @param numbers
	 * @param combination
	 * @return
	 */
	public static List<int[]> listToArrayOfPairsWithSumDivBy3(List<Integer> numbers, List<Integer> combination) {

		return numbers.stream()
				.flatMap(number -> combination.stream()
						.filter(com -> (com + number) % 3 == 0)
						.map(com -> new int[]{ number, com })
				)
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @param numbers
	 * @return
	 */
	public static Optional<Integer> findFirstEvenNumber(List<Integer> numbers) {

		return numbers.stream().filter(num -> num % 2 == 0).findFirst();
	}

	/**
	 *
	 * @param numbers
	 * @return
	 */
	public static int sumEvenNumbers(List<Integer> numbers) {

		return numbers.stream()
				.filter(num -> num % 2 == 0)
				.reduce(0, Integer::sum);
	}

	/**
	 *
	 * @param numbers
	 * @return
	 */
	public static int multiplyEvenNumbers(List<Integer> numbers) {

		return numbers.stream()
				.filter(num -> num % 2 == 0)
				.reduce(0, (num1, num2) -> num1 + num2);
	}

	/**
	 *
	 * @param numbers
	 * @return
	 */
	public static Optional<Integer> findMax(List<Integer> numbers) {

		return numbers.stream().reduce(Integer::max);
	}

	/**
	 * find min numbers of list of Integer
	 * @param numbers
	 * @return
	 */
	public static Optional<Integer> findMin(List<Integer> numbers) {

		return numbers.stream().reduce(Integer::min);
	}

	/**
	 *
	 * @param numbers
	 * @return
	 */
	public static long countElements(List<Integer> numbers) {

		return numbers.stream().count();
	}

	/**
	 * range: () exclusive
	 * rangeClosed: [] inclusive
	 * @return
	 */
	public static long countEvenBetween1And100() {

		return IntStream.rangeClosed(1, 100).filter(num -> num % 2 == 0).count();
	}

	/**
	 *
	 * @return
	 */
	public static Stream<double[]> generatePythagoreanTriples() {

		return IntStream.rangeClosed(1, 100).boxed()
				.flatMap(a ->
						IntStream.rangeClosed(a, 100)
								.mapToObj(
										b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
								.filter(t -> t[2] % 1 == 0));
	}

	/**
	 *
	 * @param arrayOfInt
	 * @return
	 */
	public static int sumOfIntArray(int[] arrayOfInt) {

		return Arrays.stream(arrayOfInt).sum();
	}

	/*********************************** PARTITIONING - START ***********************************/

	/**
	 * validate prime number
	 * @param candidate
	 * @return
	 */
	public static boolean isPrime(int candidate) {

		int squareRoot = (int) Math.sqrt((double) candidate);

		return IntStream.rangeClosed(2, squareRoot)
				.noneMatch(number -> candidate % number == 0);
	}

	/**
	 *
	 * @param numbers
	 * @param candidate
	 * @return
	 */
	public static boolean isPrime(List<Integer> numbers, int candidate) {

		int squareRoot = (int) Math.sqrt((double) candidate);

		return takeWhile(numbers, item -> item <= squareRoot)
				.stream()
				.noneMatch(number -> candidate % number == 0);
	}

	/**
	 * partitioning from 2 -> n to find which is prime numbers?
	 * boxed is use to convert int primitive into Integer object
	 * @param n
	 * @return
	 */
	public static Map<Boolean, List<Integer>> partitionPrimeNumbers(int n) {

		return IntStream.rangeClosed(2, n).boxed()
				.collect(partitioningBy(number -> isPrime(number)));
	}

	public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {

		return IntStream.rangeClosed(2, n).boxed()
				.collect(new PrimeNumbersCollector());
	}

	/*********************************** PARTITIONING - END ***********************************/



	/**
	 *
	 * @param list
	 * @param predicate
	 * @param <A>
	 * @return
	 */
	public static <A> List<A> takeWhile(List<A> list, Predicate<A> predicate) {

		int counter = 0;

		for (A item : list) {
			if (!predicate.test(item)) {
				return list.subList(0, counter);
			}

			counter++;
		}

		return list;
	}
}
