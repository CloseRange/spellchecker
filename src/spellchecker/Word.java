package spellchecker;

public class Word implements Comparable<Word> {
    private String word;
    private char[] data;

    public Word(String word) {
        this.word = word.toLowerCase();
        data = this.word.toCharArray();
    }
    public int length() {
        return data.length;
    }
    public char[] chars() {
        return data;
    }
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
    @Override
    public int compareTo(Word o) {
        Word word2 = (Word) o;

        int len = length() < word2.length() ? length() : word2.length();
        for(int i=0; i<len; i++) {
            if(data[i] != word2.data[i]) {
                return word2.data[i] - data[i];
            }
        }
        return word2.length() - length();
    }
}
