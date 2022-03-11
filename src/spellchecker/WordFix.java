package spellchecker;


import net.datastructures.LinkedPositionalList;
import net.datastructures.Position;

/**
 * Simple class used to store a mispelled word along with any potential fixes.
 */
public class WordFix {
    /**
     * Type of change suggestions
     */
    public enum Type {
        None(0), Swap(1), Insert(2), Delete(3), Replace(4), Length(5);

        private final int val;
        private Type(int val) {
            this.val = val;
        }
        /**
         * gets the numeric value of this type of change.
         * @return the numeric value of this type of change
         */
        public int value() {
            return val;
        }
    }

    private Word origional;
    private LinkedPositionalList<Word> fixes = new LinkedPositionalList<>();
    protected int[] errorCounts = new int[] { 0, 0, 0, 0, 0 };

    /**
     * Creates a base wordfix
     * @param origional the mispelled word
     */
    public WordFix(Word origional) {
        this.origional = origional;
    }
    /**
     * Gets the number of suggested fixes for this word.
     * @return the number of suggested fixes for this word
     */
    public int length() {
        return fixes.size();
    }
    /**
     * returns the number of specific fixes for a given type of change.
     * Ex. the number of suggestions that involve a swap.
     * @param type the type of change to count
     * @return the number of suggested fixes for the given type
     */
    public int count(Type type) {
        return errorCounts[type.value()];
    }
    /**
     * adds a sugggestion to the list of suggested fixes
     * @param word the suggested fixed word
     * @param type the type of change being used to fix the mispelled word
     */
    public void push(Word word, Type type) {
        if(type == Type.None) {
            return;
        }
        errorCounts[type.value()]++;

        Position<Word> p = fixes.first();
        while(p != null) {
            if(p.getElement().compareTo(word) > 0) {
                p = fixes.after(p);
            } else {
                fixes.addBefore(p, word);
                return;
            }
        }
        fixes.addLast(word);
    }

    @Override
    public String toString() {
        return toStringTrack(Type.None);
    }
    /**
     * Returns the fixes in a clean string,
     * highlighting the suggested fixes in yellow for easy debugging.
     * 
     * @param typeTrack the suggested fix to track.
     * @return the full list of fixes as a string
     */
    public String toStringTrack(Type typeTrack) {
         if(fixes.size() == 0) {
            return origional.toString() + " - No Suggestions";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(origional + " - ");
        boolean isFirst = true;
        for(Word word : fixes) {
            if(!isFirst) builder.append(", ");
            isFirst = false;
            if(SpellChecker.isCloseEdit(origional, word) == typeTrack) {
                builder.append("\u001B[33m");
                builder.append(word);
                builder.append("\u001B[0m");
            } else {
                builder.append(word);
            }
        }
        return builder.toString();
    }
}
