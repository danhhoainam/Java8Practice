package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class StringStreams {

	/**
	 * convert a passage in to an array of unique characters
	 * @param arrayOfWords
	 * @return
	 */
	public static List<String> findUniqueCharatersInText(String[] arrayOfWords) {

		return Arrays.stream(arrayOfWords)
				.map(word -> word.split("")) // convert into arrays
				.flatMap(Arrays::stream) // merge streams into one stream
				.distinct() // get distinct String values
				.collect(Collectors.toList());
	}
}
