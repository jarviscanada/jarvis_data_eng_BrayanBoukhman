package ca.jrvs.practice.codingChallenge;
/**
 * ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-b18e06bf7f2c491e8f0f0537044f079f
 */
public class FibonacciNumber {
    /**
     * Big-O: O(n)
     * Justification: it's a single for loop
     */
    public static int fibNumber(int n) {

        if(n==0) return 0;
        if(n==1) return 1;

        int result = 0;
        int prev1 = 0;
        int prev2 = 1;

        for(int i = 0; i < (n - 2); i++) {
            result = prev1 +prev2;
            prev1 = prev2;
            prev2=result;
        }
        return result;
    }
}