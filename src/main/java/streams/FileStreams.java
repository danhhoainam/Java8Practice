package streams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class FileStreams {

	/**
	 * find unique words in a text file
	 * access file path
	 * scan each line
	 * split words in a line into array
	 * merge stream of array into one
	 * distinct every words in array then count
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static long findUniqueWordsInAFile(String filePath) throws IOException {

		long uniqueWords = 0;

		try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
			uniqueWords = lines
					.flatMap(line -> Arrays.stream(line.split(" ")))
					.distinct()
					.count();
		} catch (IOException ex) {
			throw ex;
		}

		return uniqueWords;
	}
}
