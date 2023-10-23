package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    /**
     * Big-O: O(n^2)
     * Justification: Two nested loops iterate through all pairs of elements.
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1}; // No solution found
    }

    /**
     * Big-O: O(n log n)
     * Justification: The array is sorted using Arrays.sort, which has a time complexity of O(n log n).
     */
    public int[] twoSumSort(int[] nums, int target) {
        int[] sortedNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sortedNums);

        int left = 0;
        int right = sortedNums.length - 1;

        while (left < right) {
            int sum = sortedNums[left] + sortedNums[right];
            if (sum == target) {
                return new int[] {sortedNums[left], sortedNums[right]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {-1, -1}; // No solution found
    }

    /**
     * Big-O: O(n)
     * Justification: The algorithm iterates through the array once while maintaining a HashMap.
     */
    public int[] twoSumMap(int[] nums, int target) {
        Map<Integer, Integer> numToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (numToIndex.containsKey(complement)) {
                return new int[] {numToIndex.get(complement), i};
            }
            numToIndex.put(nums[i], i);
        }
        return new int[] {-1, -1}; // No solution found
    }
}
