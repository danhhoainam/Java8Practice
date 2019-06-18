package streams;

import models.Trader;
import models.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static predicates.TraderPredicates.filterTraderByCityName;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class TransactionStreams {

	/**
	 *
	 * @param transactions
	 * @param year
	 * @return
	 */
	public static List<Transaction> findAllAndSortTransactionsByYear(List<Transaction> transactions, int year) {

		return transactions.stream()
				.filter(transaction -> transaction.getYear() == year)
				.sorted(comparing(Transaction::getValue))
				.collect(toList());
	}

	/**
	 *
	 * @param transactions
	 * @return
	 */
	public static Set<String> findUniqueCitiesOfTraders(List<Transaction> transactions) {

		return transactions.stream()
				.map(transaction -> transaction.getTrader().getCity())
				.collect(Collectors.toSet());
	}

	/**
	 *
	 * @param transactions
	 * @param cityName
	 * @return
	 */
	public static List<Trader> findTradersFromCityAndSort(List<Transaction> transactions, String cityName) {

		return transactions.stream()
				.map(Transaction::getTrader)
				.filter(trader -> filterTraderByCityName.test(trader, cityName))
				.distinct()
				.sorted(comparing(Trader::getName))
				.collect(toList());
	}


	/**
	 *
	 * @param transactions
	 * @return
	 */
	public static String findAllTraderNamesAndSort(List<Transaction> transactions) {

		return transactions.stream()
				.map(Transaction::getTrader)
				.map(Trader::getName)
				.distinct()
				.sorted()
				.collect(joining());
	}

	/**
	 *
	 * @param transactions
	 * @param cityName
	 * @return
	 */
	public static boolean findAnyTraderBasedInCityName(List<Transaction> transactions, String cityName) {

		return transactions.stream()
				.anyMatch(transaction -> transaction.getTrader().getCity().equals(cityName));
	}

	/**
	 *
	 * @param transactions
	 * @param cityName
	 */
	public static void printAllValuesOfTransactions(List<Transaction> transactions, String cityName) {

		transactions.stream()
				.filter(transaction -> cityName.equals(transaction.getTrader().getCity()))
				.map(Transaction::getValue)
				.forEach(System.out::println);
	}

	/**
	 *
	 * @param transactions
	 * @return
	 */
	public static Optional<Integer> findTheHighestValueOfTransactions(List<Transaction> transactions) {

		return transactions.stream()
				.map(Transaction::getValue)
				.reduce(Integer::max);
	}

	/**
	 *
	 * @param transactions
	 * @return
	 */
	public static Optional<Transaction> findTransactionWithSmallestValue(List<Transaction> transactions) {

		return transactions.stream()
				.min(comparing(Transaction::getValue));
	}
}
