package spellchecker;

import net.datastructures.LinkedPositionalList;

/**
 * Utility class used to spell check single words
 */
public class SpellChecker {
    /**
     * tests if 2 words are equal by swapping or replacing single characters
     * @param word1 the first word to test for
     * @param word2 the second word to test for
     * @return the type of edit used to get to the destination (Type.None if not valid)
     */
    private static WordFix.Type isSwapReplace(Word word1, Word word2) {
        if(word1.length() != word2.length()) {
            return WordFix.Type.None;
        }
        int len = word1.length();
        char[] c1 = word1.chars();
        char[] c2 = word2.chars();
        boolean hasInvalid = false;
        boolean isSwap = false;
        for(int i=0; i<len; i++) {
            // symbol not the same
            if(c1[i] != c2[i]) {
                // if it already had an invalid symbol
                if(hasInvalid) {
                    // if it's NOT a swap
                    if(!(c1[i] == c2[i-1] && c1[i-1] == c2[i])) {
                        return WordFix.Type.None;
                    }
                    isSwap = true;
                }
                hasInvalid = true;
            }
        }
        return hasInvalid ? (isSwap ? WordFix.Type.Swap : WordFix.Type.Replace) : WordFix.Type.None;
    }
    /**
     * tests if 2 words are equal by adding or removing single characters
     * @param word1 the first word to test for
     * @param word2 the second word to test for
     * @return the type of edit used to get to the destination (Type.None if not valid)
     */
    private static WordFix.Type isAddRemove(Word word1, Word word2) {
        Word smaller = word1.length() < word2.length() ? word1 : word2;
        Word bigger  = word1.length() < word2.length() ? word2 : word1;

        if(smaller.length() != bigger.length() - 1) {
            return WordFix.Type.None;
        }

        char[] sChars = smaller.chars();
        char[] bChars = bigger.chars();
        int len = smaller.length();
        int sIndex = 0; // small index
        int bIndex = 0; // big index

        while(sIndex != len) {
            if(sChars[sIndex] != bChars[bIndex]) {
                if(sIndex != bIndex) return WordFix.Type.None;
                bIndex++;
            } else {
                sIndex++;
                bIndex++;
            }
        }
        return word1 == smaller ? WordFix.Type.Insert : WordFix.Type.Delete;
    }
    /**
     * fixes 2 edit types into one,
     * enforcing the rule that 2 words are only close in edit if they have 1 edit distance
     * @param type1 the first type of edit
     * @param type2 the second type of edit
     * @return the new edit
     */
    private static WordFix.Type fixTypeCheck(WordFix.Type type1, WordFix.Type type2) {
        if(type1 == WordFix.Type.None) {
            return type2;
        } else if(type2 == WordFix.Type.None) {
            return type1;
        }

        return WordFix.Type.None;
    }
    /**
     * tests if 2 words are equal by any single edit modification
     * @param word1 the first word to test for
     * @param word2 the second word to test for
     * @return the type of edit used to get to the destination (Type.None if not valid)
     */
    public static WordFix.Type isCloseEdit(Word word1, Word word2) {
        return fixTypeCheck(isSwapReplace(word1, word2), isAddRemove(word1, word2));
    }
    /**
     * iterates over all words of particular length inside dictionary finding any words
     * that are close in edit distance to the target word, and adds them to the list of 
     * fixed words
     * @param fixed the WordFix object to append to
     * @param dict the dictionary to append to
     * @param word the word to test against
     * @param size the char count to iterate over
     */
    private static void appendFixesOfSize(WordFix fixed, Dictionary dict, Word word, int size) {
        LinkedPositionalList<Word> words = dict.getList(size);
        if(words == null) {
            return;
        }
        for(Word dictWord : words) {
            fixed.push(dictWord, isCloseEdit(word, dictWord));
        }

    }
    /**
     * iterates over a dictionary to find all word fixes for a word
     * @param dict the dictionary to check against
     * @param word the mispelled word
     * @return a wordFix object with all corrected words
     */
    public static WordFix fix(Dictionary dict, Word word) {
        WordFix fixed = new WordFix(word);
        if(dict.isValid(word)) {
            return fixed;
        }
        appendFixesOfSize(fixed, dict, word, word.length() - 1);
        appendFixesOfSize(fixed, dict, word, word.length());
        appendFixesOfSize(fixed, dict, word, word.length() + 1);

        return fixed;
    }
}
