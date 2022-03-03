package processor;

public class Word {
    private String word;
    private char[] data;

    public Word(String word) {
        this.word = word;
        data = word.toCharArray();
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
}
