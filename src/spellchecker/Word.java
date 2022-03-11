package spellchecker;

/**
 * Stores a word for dictionary usage
 */
public class Word implements Comparable<Word> {
    private String word;
    private char[] data;

    public Word(String word) {
        this.word = word.toLowerCase();
        data = this.word.toCharArray();
    }

    /**
     * returns number of characters in the word
     * @return number of characters in the word
     */
    public int length() {
        return data.length;
    }
    /**
     * returns the word as a char array
     * @return the word as a char array
     */
    public char[] chars() {
        return data;
    }
    /**
     * tests of 2 words are equal
     * @return if the word is equal to this one
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Word)) {
            return false;
        }
        return ((Word)obj).word.equals(word);
    }
    @Override
    public String toString() {
        return word;
    }
    /**
     * Comaprse 2 words character by character
     */
    @Override
    public int compareTo(Word word2) {
        int len = length() < word2.length() ? length() : word2.length();
        for(int i=0; i<len; i++) {
            if(data[i] != word2.data[i]) {
                return word2.data[i] - data[i];
            }
        }
        return word2.length() - length();
    }
}
