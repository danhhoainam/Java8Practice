package collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static streams.NumberStreams.isPrime;

/**
 *
 */
public class PrimeNumbersCollector
        implements Collector<Integer,
            Map<Boolean, List<Integer>>,
            Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            put(Boolean.TRUE, new ArrayList<>());
            put(Boolean.FALSE, new ArrayList<>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            Boolean isPrimeNumber = isPrime(acc.get(Boolean.TRUE), candidate);
            acc.get(isPrimeNumber).add(candidate);
        };
    }

    /**
     * not able to work parallel
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> left, Map<Boolean, List<Integer>> right) -> {
            left.get(Boolean.FALSE).addAll(right.get(Boolean.FALSE));
            left.get(Boolean.TRUE).addAll(right.get(Boolean.TRUE));

            return left;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
