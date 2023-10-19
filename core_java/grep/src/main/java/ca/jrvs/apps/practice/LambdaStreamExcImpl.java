package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImpl implements LambdaStreamExc {
    public static void main(String[] args) {
        LambdaStreamExc lse = new LambdaStreamExcImpl();

        // Test createStrStream
        System.out.println("Testing createStrStream:");
        Stream<String> strStream = lse.createStrStream("apple", "banana", "cherry");
        strStream.forEach(System.out::println);

        // Test toUpperCase
        System.out.println("\nTesting toUpperCase:");
        Stream<String> upperCaseStream = lse.toUpperCase("hello", "world");
        upperCaseStream.forEach(System.out::println);

        // Test filter
        System.out.println("\nTesting filter:");
        Stream<String> stringStream = lse.createStrStream("apple", "banana", "cherry");
        Stream<String> filteredStream = lse.filter(stringStream, "a");
        filteredStream.forEach(System.out::println);

        // Test createIntStream
        System.out.println("\nTesting createIntStream:");
        IntStream intStream = lse.createIntStream(new int[]{1, 2, 3, 4, 5});
        intStream.forEach(System.out::println);

        // Test toList
        System.out.println("\nTesting toList (Stream of integers):");
        List<Integer> intList = lse.toList(lse.createIntStream(1, 5));
        intList.forEach(System.out::println);

        // Test createIntStream with range
        System.out.println("\nTesting createIntStream (range):");
        IntStream rangeStream = lse.createIntStream(1, 5);
        rangeStream.forEach(System.out::println);

        // Test squareRootIntStream
        System.out.println("\nTesting squareRootIntStream:");
        IntStream squareRootStream = lse.createIntStream(1, 5);
        DoubleStream doubleStream = lse.squareRootIntStream(squareRootStream);
        doubleStream.forEach(System.out::println);

        // Test getOdd
        System.out.println("\nTesting getOdd:");
        IntStream oddStream = lse.createIntStream(1, 10);
        IntStream filteredOddStream = lse.getOdd(oddStream);
        filteredOddStream.forEach(System.out::println);

        // Test getLambdaPrinter
        System.out.println("\nTesting getLambdaPrinter:");
        Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
        printer.accept("Message body");

        // Test printMessages
        System.out.println("\nTesting printMessages:");
        String[] messages = {"Hello", "World", "Java"};
        lse.printMessages(messages, System.out::println);
    }
    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.stream(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string -> !string.contains(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(number -> number % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return message -> System.out.println(prefix + message + suffix);
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Arrays.stream(messages).forEach(printer);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream).boxed().forEach(n -> printer.accept(String.valueOf(n)));
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(List::stream).map(n -> n*n);
    }
}
