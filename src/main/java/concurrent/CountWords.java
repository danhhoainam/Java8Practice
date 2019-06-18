package concurrent;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CountWords {

    public static int countWordsIteratively(String sentence) {

        int counter = 0;
        boolean lastSpace = true;
        for (char c: sentence.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }

        return counter;
    }

    public static int countWordsWithWordCounter(String sentence) {

        Stream<Character> stream = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        return countWordsWithWordCounter(stream);
    }

    public static int countWordsParallelWithWordCounter(String sentence) {

        Stream<Character> stream = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        return countWordsWithWordCounter(stream.parallel());
    }

    public static int countWordsWithWordCounter(Stream<Character> stream) {

        WordCounter counter = stream.reduce(
                new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine
        );
        return counter.getCounter();
    }

    public static int countWordsWithWordCounterSpliterator(String sentence) {

        Spliterator<Character> spliterator = new WordCounterSpliterator(sentence);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        return countWordsWithWordCounter(stream);
    }
}
