package streams;

import java.util.stream.Stream;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class InfiniteStreams {

	/**
	 * iterate: (init value, UnaryOperator)
	 */
	public static void generateEvenNumbers() {

		Stream.iterate(0, a -> a + 2) // in java 9 we have predicate to replace limit
				.limit(10) // in java 9, we can use takeWhile for short circuit
				.forEach(System.out::println);
	}

	/**
	 * iterate: (init value, UnaryOperator)
	 */
	public static void generateFibonanciSequence() {

		Stream.iterate(new int[]{0, 1}, arr -> new int[] { arr[1], arr[0] + arr[1] }) // in java 9 we have predicate to replace limit
				.limit(20) // in java 9, we can use takeWhile for short circuit
				.map(arr -> arr[0])
				.forEach(System.out::println);
	}

	/**
	 * generate: use Supplier
	 */
	public static void generateRandomNumbers() {

		Stream.generate(Math::random)
				.limit(20)
				.forEach(System.out::println);
	}

	public static long sequentialSum(long n) {

		return Stream.iterate(1L, i -> i + 1)
				.limit(n)
				.reduce(0L, Long::sum);
	}

	public static long parallelSum(long n) {

		return Stream.iterate(1L, i -> i + 1)
				.limit(n)
				.parallel()
				.reduce(0L, Long::sum);
	}
}
