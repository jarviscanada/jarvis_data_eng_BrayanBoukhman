package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class ValidParentheses {
    /**
     * Validates the balance of parentheses in a given string.
     *
     * Time Complexity: O(n)
     * - The algorithm iterates through the characters of the input string once, where 'n' is the length of the string.
     * - During each iteration, it performs stack operations in constant time.
     * - The time complexity is linear, making this an efficient solution for parentheses validation.
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else if (c == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else {
                return false; // Unmatched closing parenthesis or other characters
            }
        }

        return stack.isEmpty(); // If the stack is empty, all parentheses are balanced.
    }
}
