package collections;

import bootstrap.TransactionBootstrap;
import models.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionFactories {

    public static void initListExample() {

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

    public static void initSetExample() {

        // set from list
        Set<String> set1 = new HashSet<>(Arrays.asList("45", "54")); // mutable

        // set from stream
        Set<String> set2 = Stream.of("1", "2").collect(Collectors.toSet());

        // java 9
        // Set<String> set3 = Set.of("123", "456");
    }

    public static void initMapExample() {

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

    public static void listRemoveIfExample() {

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

    public static void listReplaceAllExample() {

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

    public static void hashmapExample() {

        Map<String, String> map1 = new HashMap<>();
        map1.put("Name 2", "Num 2");
        map1.put("Name 1", "Num 1");
        map1.put("Name 5", "Num 5");
        map1.put("Name 4", "Num 4");

        Map<String, String> map2 = new HashMap<>();
        map2.put("Name 3", "Num 3");
        map2.put("Name 6", "Num 6");

        // iterate old way
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key: " + key + "; value: " + value);
        }

        // iterate by java 8
        map1.forEach(showKeyValue);

        // sort by keys, values
        // for performance, key should be String or Number
        // that we can use the entries as sorted tree with O(log(n)) retrieval
        // if not, default is LinkedList with O(n) retrieval
        map1.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);

        // getOrDefault
        System.out.println(map1.getOrDefault("Num 3", "Test"));

        // map has 3 compute functions: computeIfAbsent, computeIfPresent, compute
        Map<String, List<String>> todoList = new HashMap<>();
        todoList.computeIfAbsent("taking_notes", generateList).add("coding function 1");

        // remove
        map1.remove("Name 1", "Num 1");

        // replace All
        map1.replaceAll((key, value) -> value.toUpperCase());

        // how to merge 2 maps
        Map<String, String> mergedMap = new HashMap<>(map1);
        map2.forEach((key, value) -> mergedMap.merge(key, value, (left, right) -> left + " & " + right));
    }

    public static void concurrentHashmapExample() {

        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        // find max values
        long parallelismThreshold = 1;
        Optional<Long> maxValue =
                Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));

        // count
        long numberOfElements = map.mappingCount();

        // search
        map.search(parallelismThreshold, (key, value) -> "1".equals(key) && value != null);

        // KeySetView
        ConcurrentHashMap.KeySetView<String, Long> keySetView = map.keySet();
        map.newKeySet();
    }

    private static Predicate<Transaction> valueIs1000 = transaction -> transaction.getValue() == 1000;

    private static UnaryOperator<String> uppercaseFirstLetter =
            item -> Character.toUpperCase(item.charAt(0)) + item.substring(1);

    private static BiConsumer<String, String> showKeyValue =
            (key, value) -> System.out.println("key: " + key + "; value: " + value);

    private static Function<String, List<String>> generateList = value -> new ArrayList<>();
}
