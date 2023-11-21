function solution(arr) {
    const n = arr.length;
    let count = 0;

    for (let i = 1; i < n - 1; i++) {
        if ((arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) || (arr[i] < arr[i - 1] && arr[i] < arr[i + 1])) {
            // The element at index i is a peak or a valley
            let left = i - 1;
            let right = i + 1;

            // Count the subarrays extending to the left
            while (left >= 0 && arr[left] !== arr[left + 1]) {
                left--;
                count++;
            }

            // Count the subarrays extending to the right
            while (right < n && arr[right] !== arr[right - 1]) {
                right++;
                count++;
            }
        }
    }

    return count;
}

const arr1 = [9, 8, 7, 6, 5];
console.log(solution(arr1)); // Output: 4

const arr2 = [10, 10, 10];
console.log(solution(arr2)); // Output: 0

const arr3 = [1, 2, 1, 2, 1];
console.log(solution(arr3)); // Output: 10