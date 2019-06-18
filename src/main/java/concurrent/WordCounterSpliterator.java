package concurrent;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {

    private final String sentence;
    private int currentChar = 0;

    public WordCounterSpliterator(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(sentence.charAt(currentChar++));
        return currentChar < sentence.length();
    }

    @Override
    public Spliterator<Character> trySplit() {

        int currentSize = sentence.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }

        for (int splitPos = currentSize / 2 + currentChar; splitPos < sentence.length(); splitPos++) {
            if (Character.isWhitespace(sentence.charAt(splitPos))) {
                Spliterator<Character> spliterator =
                        new WordCounterSpliterator(sentence.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }

        return null;
    }

    @Override
    public long estimateSize() {
        return sentence.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
