/*
1. TWO SUM
Given an array of integers 'nums' and an integer 'target', return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.

EXPLANATION: So, we are given an array of integers called nums and a target integer called target.
             Our task is to find two different numbers in the array that add up to the target.
             We must return the indices of these two numbers.

Example 1:
   Input: nums = [2,7,11,15], target = 9
   Output: [0,1]
   Explanation: Because nums[0] + nums[1] == 9, we return [0,1]
Example 2:
   Input: nums = [3,2,4], target = 6
   Output: [1,2]
Example 3:
   Input: nums = [3,3], target = 6
   Output: [0,1]

CONSTRAINTS:
   * 2 <= nums.length <= 10^4
      - The input array must have atleast 2 numbers (because we need a pair)
      - The array can have up to 10,000 numbers.
      - This tells us the algorithm must be efficient:
        - O(n^2) might still pass but could be slow at the upper limit.
        - O(n) using a hash map is ideal.
   * -10^9 <= nums[i] <= 10^9
      - Each number in the array can be as small as -1,000,000,000 and as large as +1,000,000,000
      - These values fit easily in standard 32-bit integers.
      - Since the range is large, solutions that rely on counting arrays (like frequency arrays indexed by value) are Not feasible.
   * -10^9 <= target <= 10^9
      - The largest sum can also be a large negative or positive number.
      - No special restrictions - as long as pairwise sum fit in 32-bit integer.
   * Only one valid answer exists
      - Exactly one pair of indices satisfies the condition.
      - You dont need to worry about: multiple pairs, returning all solutions, or ambiguous results.
      - As soon as we find a valid pair, we can return it.
 */

/*
SOLUTION:
    * BRUTE FORCE SOLUTION:
        The brute force method will check every possible pair of numbers in the array.
        This can be better understood with an example:
        Taking int[] nums = {2,7,11,15} and target = 9
            Step 1: We'll select 2 to be our first element and then we will check with all other remaining elements if sum == target.
            Step 2: Then leaving 2, we check with next element 7. This adds up to the given target 9.
            Step 3: Now as the pair has been found, we return the indices of both elements. Output: [0,1]
        Another example: int[] nums = {3,2,4} and target = 6
            Step 1: We'll select the first element 3 and check wth the rest of the array elements if sum adds up to target.
            Step 2: The second element is 2. adding 3 + 2 = 5 which is not equal to target. so we move to next element.
            Step 3: The third element is 4. 3 + 4 = 7 which is not equal to the target. Now we dont have more elements to add with 3.
            Step 4: Now we try and compare with the second element 2. the next element to it is 4. Adding 2 + 4 = 6 == target.
            Step 5: Output [1,2]
            NOTE: when considering 2 as the first element, we dont add it with 3 which is at index 1, because we have already checked it in the first iteration.

       This dry run gives us the brute force solution.
       What we need:
        - we need one for loop to keep track of the first element.
        - we need another for loop to add the first element to all other elements in the array.
        This solution works because we try all combinations possible to find the solution.

        TIME AND SPACE COMPLEXITY:
            - Time complexity: O(n^2)
                - When i = 0; j loops ~ n-1 times
                - When i = 1; j loops ~ n-2 times
                - When i = 2; j loops ~ n-3 times
                - And so on, When i = n-2, j loops 1 time.
                - So total number of operations: n(n-1)/2 ~ n^2/2 -> O(n^2)
            - Space complexity: O(1)
                - no new data structures
                - only two integer variables i, j. This is a fixe amount of memory.

    * OPTIMIZED SOLUTION:
        Instead of checking all pairs, we use a HashMap to remember numbers we've already seen.
        This lets us find the required pair in O(1) time per element.
        For each number, we check instantly if its complement exists. This eliminates the need for nested loops.

        TIME AND SPACE COMPLEXITY:
            - Time complexity: O(n)
                - The loop runs n times. n is the length of the array.
                - inside each iteration we do :
                    - compute complement: O(1)
                    - map.containsKey: O(1)
                    - map.put(): O(1)
                    - return result: O(1) when found
                - All operations inside the loop are constant time, O(1)
            - Space complexity: O(n)
                The HashMap stores numbers as keys and their indices as values.
                Worst case:
                    - The solution is found at the last element.
                    - This means that we have inserted all previous n-1 elements into the map. So the map contains n elements.
                    - So, extra space = O(n)
 */
// ****************************************************************************************************************************************************
// ****************************************************************************************************************************************************



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args){

        int[] nums = {2,7,11,15};                                               // integer array taken for testing
        int target = 9;                                                         // integer target for testing
        System.out.println(Arrays.toString(twoSum(nums, target)));              // Printing out the twoSum method using the Arrays.toString because twoSum gives us an int[] array
        System.out.println(Arrays.toString(twoSumOptimized(nums, target)));     // Same but calls for optimized Solution.
    }

    // BRUTE FORCE SOLUTION:
    public static int[] twoSum(int[] nums, int target){

        for(int i = 0; i < nums.length ; i++){                  // This is the outer loop. The purpose is to pick the first element.
            for(int j = i + 1; j < nums.length; j++){           // This is the inner loop. j = i + 1 makes sure that we dont check the same index twice or repeat pairs in reverse.
                                                                // The purpose of this loop is to pair the element nums[i] with every element in nums[j]
                if(nums[i] + nums[j] == target){                // checks whether the current pair of elements sums to target. nums[i] + nums[j] computes the sum, == target compares it to target value.
                    return new int[]{i,j};                      // if the condition is true. this line executes. it returns a new integer array with the two indices [i,j]
                }
            }
        }
        throw new IllegalArgumentException("Match not found");  // If no pair sums to the target, this line throws an exception.
    }

    // OPTIMIZED VERSION USING HASH MAPS:
    public static int[] twoSumOptimized(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();                // Create a HGashMap with key: number from the array and value: its index.

        for(int i = 0 ; i < nums.length; i ++){                     // Loops through the array once.
            int complement = target - nums[i];                      // calculates the complement: the number needed to reach the target
            if(!map.containsKey(complement)){                       // checks if the complement has not been seen before. if its not in the map, it means we havent found a pair for nums[i]
                map.put(nums[i], i);                                // saves this number and its index in the map. so future numbers can find it as complement.
            }else{                                                  // this executes when the complement is found in the map, means we have the two numbers that add up to target.
                return new int[] {map.get(complement),i};           // returns the two indices: map.get(complement) -> the index of the earlier number. i -> index of the current number.
            }
        }
        throw new IllegalArgumentException("Match not found");      // if the loop ends without finding a pair, throw an exception.
    }


}
