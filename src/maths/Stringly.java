package maths;

import processor.Word;

public class Stringly {
    private static boolean isSwapReplace(Word word1, Word word2) {
        if(word1.length() != word2.length()) {
            return false;
        }
        int len = word1.length();
        char[] c1 = word1.chars();
        char[] c2 = word2.chars();
        boolean hasInvalid = false;
        for(int i=0; i<len; i++) {
            // symbol not the same
            if(c1[i] != c2[i]) {
                // if it already had an invalid symbol
                if(hasInvalid) {
                    // if it's NOT a swap
                    if(!(c1[i] == c2[i-1] && c1[i-1] == c2[i])) {
                        return false;
                    }
                }
                hasInvalid = true;
            }
        }
        return hasInvalid;
    }
    private static boolean isAddRemove(Word word1, Word word2) {
        Word smaller = word1.length() < word2.length() ? word1 : word2;
        Word bigger  = word1.length() < word2.length() ? word2 : word1;

        if(smaller.length() != bigger.length() - 1) {
            return false;
        }
        char[] sChars = smaller.chars();
        char[] bChars = bigger.chars();
        int len = smaller.length();
        int sIndex = 0; // small index
        int bIndex = 0;
        while(sIndex != len-1) {
            //System.out.printf("{%s, %s}\n", sChars[sIndex], bChars[bIndex]);
            if(sChars[sIndex] != bChars[bIndex]) {
                if(sIndex != bIndex) {
                    return false;
                } else {
                    bIndex++;
                }
            } else {
                sIndex++;
                bIndex++;
            }

        }

        return true;
    }
    public static boolean isCloseEdit(Word word1, Word word2) {
        return isSwapReplace(word1, word2) ^ isAddRemove(word1, word2);
    }
}
