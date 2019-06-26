import com.google.common.base.Throwables;
import concurrent.CountWords;
import enums.Gender;
import models.Student;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streams.InfiniteStreams;
import streams.NumberStreams;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static predicates.StudentPredicates.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    final static String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                    "mi  ritrovai in una  selva oscura" +
                    " ché la  dritta via era   smarrita " +
                    "  ché la  dritta via era   smarrita";

    public static void main(String[] args) {

        neverMutateVarWithStream();

        testSumBenchmark();

        System.out.println("count words Iteratively: " + CountWords.countWordsIteratively(SENTENCE));
        System.out.println("count words with counter: " + CountWords.countWordsWithWordCounter(SENTENCE));
        System.out.println("count words parallel with counter: "
                + CountWords.countWordsParallelWithWordCounter(SENTENCE));
        System.out.println("count words parallel with spliterator counter: "
                + CountWords.countWordsWithWordCounterSpliterator(SENTENCE));
    }

    public static void neverMutateVarWithStream() {

        List<String> test1 = new ArrayList<>();
        IntStream.rangeClosed(0, 50)
                .boxed()
                .parallel()
                .forEach(item -> test1.add(String.valueOf(item)));
        List<String> test2 = IntStream.rangeClosed(0, 50)
                .boxed()
                .parallel()
                .map(String::valueOf)
                .collect(Collectors.toList());

        System.out.println(test1);
        System.out.println(test1.size());
        System.out.println(test2);
        System.out.println(test2.size());
    }

    public static void testPrimesBenchmark() {

        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();

            NumberStreams.partitionPrimeNumbers(1_000_000);

            long end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (duration < fastest) fastest = duration;
        }
        System.out.println("fastest execution of normal partition: " + fastest + " ms");

        fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();

            NumberStreams.partitionPrimesWithCustomCollector(1_000_000);

            long end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (duration < fastest) fastest = duration;
        }
        System.out.println("fastest execution of custom collector partition: " + fastest + " ms");
    }

    public static void testSumBenchmark() {

        long start = System.nanoTime();
        InfiniteStreams.sequentialSum(10_000_000L);
        long end = System.nanoTime();
        System.out.println("Duration sequential sum: " + ((end - start) / 1_000_000) + " ms");

        // parallel sum is much more slower than the sequential sum
        // why?
        // because of the boxing mechanism?
        long start1 = System.nanoTime();
        InfiniteStreams.parallelSum(10_000_000L);
        long end1 = System.nanoTime();
        System.out.println("Duration parallel sum: " + ((end1 - start1) / 1_000_000) + " ms");
    }

    public static void testExceptionLogging() {

        try {
            throw new RuntimeException("test 1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            throw new RuntimeException("test 2");
        } catch (Exception ex) {
            logger.error("I want to test Logger: {}", "testttttt", ex);
        }

        try {
            throw new RuntimeException("Test 3");
        } catch (Exception ex) {
            logger.error("I want to test Throwables: {}", Throwables.getStackTraceAsString(ex));
        }
    }

    /**
     * should be improved with Pattern.compile() for better performance
     *
     * @param value
     * @return
     */
    public static String standardized(String value) {

        String nonNullValue = Optional.ofNullable(value).orElse("");
        String newValue = Normalizer.normalize(nonNullValue, Normalizer.Form.NFD);

        return newValue
                .replaceAll("[\\p{M}]", "")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll(" ", "\\_")
                .toLowerCase();
    }

    public static String standardizedWithStringUtils(String value) {

        String valueWithoutAccents = StringUtils.stripAccents(value);
        String valueWithoutSpecialChars = valueWithoutAccents.replaceAll("[^a-zA-Z0-9 ]", "");
        String normalizedSpacesValue = StringUtils.normalizeSpace(valueWithoutSpecialChars);

        return normalizedSpacesValue.replaceAll(" ", "\\_");
    }

    public static void testPredicates() {

        Student student1 = new Student(1L, "Student 1", 20, Gender.MALE, 25, 99);
        Student student2 = new Student(2L, "Student 2", 18, Gender.FEMALE, 40, 99);
        Student student3 = new Student(3L, "Student 3", 25, Gender.MALE, 1, 99);
        Student student4 = new Student(4L, "Student 4 SH", 30, Gender.FEMALE, 14, 99);

        System.out.println("Student 2 is Female? " + isFemaleStudent.test(student2));

        System.out.println("Student 3 is Male and Pass? " + isFemaleStudent.and(isStudentPassed).test(student3));

        System.out.println("Student 1 is Male or Pass? " + isMaleStudent.or(isStudentPassed).test(student1));

        System.out.println("Student 4 passed? " + isStudentPassed.negate().negate().test(student4));

        Predicate<String> isStudent1 = Predicate.isEqual("Student 1");
        System.out.println("Check student 1? " + isStudent1.test(student1.getName()));

        System.out.println(isLessThan30.test(student2.getMarks()));

        System.out.println(isCorrectId.test(student1.getId()));

        System.out.println(isCorrectFee.test(student4.getTuitionFee()));

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        filterStudent(isFemaleStudent.and(isStudentPassed), students).forEach(System.out::println);
    }
}
