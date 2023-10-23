package ca.jrvs.practice.codingChallenge;

public class ClimbingStairs {
    /**
     * Big-O: O(2^n)
     * Justification: The recursive approach calculates Fibonacci-like values
     * for each step, resulting in an exponential number of redundant calculations.
     */
    public static int climbStairsRecursive(int n) {
        if (n <= 1) {
            return 1;
        }
        return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
    }
    /**
     * Big-O: O(n)
     * Justification: The dynamic programming approach uses an array to store
     * computed values for each step, avoiding redundant calculations. It has linear time complexity.
     */
    public static int climbStairsDynamicProgramming(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
