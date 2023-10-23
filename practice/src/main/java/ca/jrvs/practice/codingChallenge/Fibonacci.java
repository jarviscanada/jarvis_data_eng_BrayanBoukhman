package ca.jrvs.practice.codingChallenge;

public class Fibonacci {
    /**
     * Big-O: O(2^n)
     * Justification: The recursive function has exponential time complexity because it calculates
     * the same Fibonacci values multiple times, leading to a large number of redundant calculations.
     */
    public static int fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    /**
     * Big-O: O(n)
     * Justification: The dynamic programming approach iteratively computes Fibonacci numbers in a
     * bottom-up manner, avoiding redundant calculations. It has a linear time complexity.
     */
    public static int fibonacciDynamicProgramming(int n) {
        if (n <= 1) {
            return n;
        }

        int[] fib = new int[n + 1];
        fib[0] = 0;
        fib[1] = 1;

        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        return fib[n];
    }
}
