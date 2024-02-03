import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author JhaDeya Rhymes
 * @version 1.0
 * @userid jrhymes7
 * @GTID 903588553
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern can't be null or length 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and comparator can't be null.");
        }
        List<Integer> list = new ArrayList<>();
        int length = text.length();
        int plength = pattern.length();
        int i = 0;
        int x = 0;
        if (length >= plength) {
            while (i <= length - plength) {
                x = 0;
                while ((x < plength) && comparator.compare(text.charAt(i + x), pattern.charAt(x)) == 0) {
                    x++;
                }
                if (x == plength) {
                    list.add(i);
                }
                i++;
            }
        }
        return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern or comparator is null");
        }
        int[] table = new int[pattern.length()];
        if (pattern.length() != 0) {
            table[0] = 0;
        }
        int i = 0;
        int x = 1;
        while (x < pattern.length()) {
            if (comparator.compare(pattern.charAt(x), pattern.charAt(i)) == 0) {
                table[x++] = ++i;
            } else {
                if (i != 0) {
                    i = table[i - 1];
                } else {
                    table[x++] = i;
                }
            }
        }
        return table;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern can't be null or length 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and comparator can't be null.");
        }
        List<Integer> list = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return list;
        }
        int[] tmp = buildFailureTable(pattern, comparator);
        int idx = 0;
        int pIdx = 0;
        while (idx + pattern.length() <= text.length()) {
            while (pIdx < pattern.length()
                    && comparator.compare(pattern.charAt(pIdx),
                    text.charAt(idx + pIdx)) == 0) {
                pIdx++;
            }
            if (pIdx == 0) {
                idx++;
            } else {
                if (pIdx == pattern.length()) {
                    list.add(idx);
                }
                idx += pIdx - tmp[pIdx - 1];
                pIdx = tmp[pIdx - 1];
            }
        }
        return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is null");
        }
        Map<Character, Integer> table = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern can't be null or length 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and comparator can't be null.");
        }
        List<Integer> list = new ArrayList<>();
        Map<Character, Integer> table = buildLastTable(pattern);
        int idx = 0;
        while (idx + pattern.length() <= text.length()) {
            int pIdx = pattern.length() - 1;
            while (pIdx >= 0 && comparator.compare(
                    text.charAt(idx + pIdx), pattern.charAt(pIdx)) == 0) {
                pIdx--;
            }
            if (pIdx < 0) {
                list.add(idx++);
            } else {
                int shift = table.getOrDefault(
                        text.charAt(idx + pattern.length() - 1), -1);
                if (shift < pIdx) {
                    idx += pIdx - shift;
                } else {
                    idx++;
                }
            }
        }
        return list;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
