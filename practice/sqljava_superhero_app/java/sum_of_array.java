package ca.jrvs.practice.codingChallenge.temp;

public class sum_of_array {

    public static int prevSum = 0;
    public static int prevLastInt = 0;
    public static int prevFirstInt = 0;

    public static int firstCount (int[] array, int len, int n) {;
        int total = 0;
        if(n >= len) {
            for(int i = 0; i<len-1; i++) {
                total+= array[i];
            }
            prevLastInt = n;
            total = total + n;
        }
        else {
            for(int i = 0; i<len; i++) {
                total+= array[i];
            }
            prevLastInt = 1;
            prevFirstInt = len-1;
        }
        prevSum = total;
        return total;
    }
    public static int count (int[] array, int len, int n) {
        prevSum = 100;
        if(n >= len && n != prevLastInt && n != prevFirstInt) {
            prevSum = prevSum - prevLastInt + n;
            prevLastInt = n;
        }
        if(n >= len && n == prevFirstInt) {
            int temp = prevFirstInt;
            prevFirstInt = prevLastInt;
            prevFirstInt = temp;
        }
        if(n >= len && n == prevLastInt) {
            int temp = prevFirstInt;
            prevFirstInt = prevLastInt;
            prevFirstInt = temp;
        }
        return prevSum;
    }
}
