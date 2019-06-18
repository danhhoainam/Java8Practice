package collections;

import bootstrap.TransactionBootstrap;
import models.Transaction;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionFactories {

    public static void listExample() {

        // old style
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");

        // Arrays as list
        List<String> list2 = Arrays.asList("1", "2", "3"); // immutable list
        list2.set(0, "4"); // still can update this list
        // list2.add("5"); --> exception

        // java 9
        // List<String> list3 = List.of("1", "2");
        // unable to set or add --> fully immutable
    }

    public static void setExample() {

        // set from list
        Set<String> set1 = new HashSet<>(Arrays.asList("45", "54")); // mutable

        // set from stream
        Set<String> set2 = Stream.of("1", "2").collect(Collectors.toSet());

        // java 9
        // Set<String> set3 = Set.of("123", "456");
    }

    public static void mapExample() {

        // old style
        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "2");

        // java 9
        // Map<String, String> map 2 = Map.of("1", "abc", "2", "def");

        // java 9
//        Map<String, Integer> ageOfFriends
//                  = Map.ofEntries(entry("Raphael", 30),
//                                  entry("Olivia", 25),
//                                  entry("Thibaut", 26));
        // have to import static java.util.Map.entry;
    }

    public static void removeIfExample() {

        // this code could throw ConcurrentModificationException
        List<Transaction> transactions = TransactionBootstrap.init();
        for (Transaction transaction : transactions) {
            if (transaction.getValue() == 1000) {
                transactions.remove(transaction);
            }
        }

        // to fix problem above --> iterator
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = iterator.next();
            if (transaction.getValue() == 1000) {
                iterator.remove();
            }
        }

        // or fix by removeIf in java 8
        transactions.removeIf(valueIs1000);
    }

    public static void replaceAllExample() {

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");

        // replace all by stream
        // create new list of string
        List<String> newList = list1.stream()
                .map(uppercaseFirstLetter)
                .collect(Collectors.toList());

        // what about mutate the old list?
        list1.replaceAll(uppercaseFirstLetter);
    }

    private static Predicate<Transaction> valueIs1000 = transaction -> transaction.getValue() == 1000;

    private static UnaryOperator<String> uppercaseFirstLetter =
            item -> Character.toUpperCase(item.charAt(0)) + item.substring(1);
}
