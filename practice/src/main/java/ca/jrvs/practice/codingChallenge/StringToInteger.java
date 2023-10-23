package ca.jrvs.practice.codingChallenge;

public class StringToInteger {
    /**
     * Big-O: O(n)
     * Justification: The built-in 'Integer.parseInt()' method processes each character of the string
     * once to convert it to an integer, resulting in a linear time complexity.
     */
    public static int stringToIntBuiltIn(String str) {
        return Integer.parseInt(str);
    }

    /**
     * Big-O: O(n)
     * Justification: The custom implementation iterates through each character of the string
     * once to calculate the integer value, resulting in a linear time complexity.
     */
    public static int stringToIntCustom(String str) {
        int result = 0;
        int sign = 1;
        int i = 0;

        // Handle the sign (if present)
        if (!str.isEmpty() && (str.charAt(0) == '+' || str.charAt(0) == '-')) {
            if (str.charAt(0) == '-') {
                sign = -1;
            }
            i = 1; // Move to the next character
        }

        while (i < str.length()) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                int digit = c - '0';
                result = result * 10 + digit;
                i++;
            } else {
                break; // Stop on encountering non-digit characters
            }
        }

        return result * sign;
    }
}
