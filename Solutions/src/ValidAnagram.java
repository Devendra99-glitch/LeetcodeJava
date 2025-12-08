/*
242. VALID ANAGRAM
Given two strings s and t, return true if t is an anagram of s, and false otherwise.

EXPLANATION: So, we are given two strings s and t. We return true if t is an anagram of s, and false otherwise.
             What is an anagram?
                Two strings are anagrams if
                   - They contain the same characters
                   - With the same frequencies
                   - Order does not matter


Example 1:
   Input: s = "anagram", t = "nagaram"
   Output: True
Example 2:
   Input: s = "rat", t = "car"
   Output: false

CONSTRAINTS:
    * 1 <= s.length, t.length <= 5 * 10^4
        - The smallest possible string length is 1 and the largest possible string length is 50,000
        - This would mean that comparing each character of s with every character of t will be too slow.
        - so it will rule out any approach slower than O(n log n)
    * s and t consist of lowercase English letters
        - only characters from 'a' to 'z' will be in the input.
            - this means that we don't need to worry about uppercase, numbers, symbols or Unicode
        - which is exactly 26 characters.
            - this means we can use a fixed array of size 26 which will make the solution fast, O(1) space and no need for HashMap
 */

/*
SOLUTION:
    * BRUTE FORCE SOLUTION:
        The approach would be to match every character in s, with a character in t and mark it as used.
        This is straightforword but an inefficient approach because we repeatedly scan t for each character of s.
        For this, we can:
            - first check if the lengths of the two strings are equal, then only it can be an anagram. If not equal return false.
            - convert t into a list or an array.
            - for each character c in s:
                - we will search through t to find the same character.
                - if found:
                    - we will mark that character as "used" by replacing the character with a special marker.
                - if not found:
                    - we will return false as t does not have the same set of characters as s
            - After processing all characters, if all characters of s were matched, we will return true.

         TIME AND SPACE COMPLEXITY:
            - Time complexity: O(n^2)
                - let n is the length of strings. s.length == t.length
                - Outer loop runs once per character in s -> n iteration
                - Inner loop for each character in s, we will scan the entire t array to find a match -> so upto n scans
                - O(n^2) which is very slow compared to the string length constraints given to us.
            - Space complexity: O(n)
                - so in this approach we convert t into an array, so that the characters can be marked as used.
                - that array length is n
                - every other computation is O(1) space
                - O(n)

    * OPTIMIZED SOLUTION:
        This approach is focused on the frequency of characters in both strings and their comparison
        For this,
            - we first check the lengths of both the strings. this is done to eliminate any work to be done if the strings are not of the same length.
            - then we will create an array for counting the frequency of characters. This array will store the number of times the character appears in the string.
                - index 0 -> 'a'
                - index 1 -> 'b' and index 25 -> 'z'
            - we will process both strings in the same loop. this means that for every position i, we will increment the count for s[i] and decrement the count for t[i].
                - if both strings contain the same number of each letter: then increments - decrements = 0
            - then finally after processing both the strings, we will check frequency array if all the counts are 0.
            - if for any character in the array the count is not 0, then it means the frequency of that character in both strings is different.

         TIME AND SPACE COMPLEXITY:
            - Time complexity: O(n)
                - length check -> O(1)
                - the loop runs for n times, accessing charAt(i) and array inc/dec -> O(1)
                - checking charCount array -> O(1) as array size is fixed and not dependent on the strings.
                - total: O(n)
            - Space complexity: O(1)
                - integer array of size 26 -> O(1)
 */



public class ValidAnagram {
    public static void main(String[] args){
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagramBrute(s,t));
        System.out.println(isAnagram(s,t));
    }

    public static boolean isAnagramBrute(String s, String t){
        if(s.length() != t.length()){                       // the very first check is to determine if the lengths of both strings are same, then only they will contain the same characters.
            return false;                                   // if they are not true, then we return false. because then it is not an anagram.
        }                                                   // remember anagram is just a rearrangement of characters, but the character count remains same.

        char[] arr = t.toCharArray();                       // we convert the string t to an array of characters -> String t = "car", then the character array will be like {'c','a','r'}

        for(int i = 0; i < s.length(); i++){                // now we initialize a loop to go through the string s character by character
            char c = s.charAt(i);                           // we store the character at the ith location in string s in a character variable. used for comparing
            boolean found = false;                          // we set a flag found to false initially. this flag will later account for if the character has been found or not.

            for(int j = 0; j < arr.length; j++){            // we initialize a second loop to iterate over the character array of string t that we created earlier.
                if(arr[j] == c){                            // if the character at jth position in the array is equal to the character stored in the variable
                    arr[j] = '@';                           // then we set the character in the array with a special character, so that when we recheck that character is not available. This is incase if there are repetitive characters.
                    found = true;                           // then we set the flag to true, meaning that the character has been found.
                    break;                                  // then we break out of the loop so that the outer loop can be incremented to check for the next character.
                }                                           // NOTE: using break makes sure that once the match is found, we dont want to keep searching for the same character, as repetitive characters would also be marked out.
            }
            if(!found){                                     // if going through both loops we do not find matching characters, we check here
                    return false;                           // if not found then we return false. false meaning it is not an anagram.
            }
        }
        return true;                                        // but incase it was found, then we return true.
    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;         // the very first check is to determine if the lengths of both strings are same, then only they will contain the same characters.

        int[] charCount = new int[26];                      // we create an integer array of size 26 to hold the counts for each lowercase letter ('a' to 'z'). Each letter corresponds to an index: 0 => 'a'. initial values all being zero.

        for(int i = 0; i< s.length(); i++){                 // we initialize a loop that iterates once per character position. both strings have the same length, so we can use length of any string
            charCount[s.charAt(i) - 'a']++;                 // so we take the ith character in string s and convert it into an index by subtracting 'a'. EXAMPLE: if s.charAt(i) is 'm', then 'm' - 'a' gives 12. then we increment the count at that index from 0 to 1. meaning the character has appeared once in the string.
            charCount[t.charAt(i) - 'a']--;                 // so in this string t, we calculate the index of the character and then decrement the count at that index, meaning one of this letter has appeared in t
                                                            // we increment in string s and decrement in string t for occurrences because if it is an anagram, the final count of each character will come out to 0.
        }

        for(int count : charCount){                         // this is an enhanced for loop that iterates over each element in the charCount array. so each element in this array stores the occurrence count of each alphabet.
            if(count != 0){                                 // if any of the counts (elements) is not 0, it means the number of occurrences of that letter differ in s and t. meaning if there are 3 a's in string s and less than 3 a's in string t. then count will be 1.
                return false;                               // we will return false. because if any of the counts is non zero, this means that the same characters are not present in both strings. meaning they are not anagrams.
            }
        }
        return true;                                        // if we completed the loop, and every other count is 0, this means that every character present in string s is equal to the characters in string t. Example: if there are 3 a's in both string s and t. So this means that they are anagrams.

    }

}
