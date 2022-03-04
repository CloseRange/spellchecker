package spellchecker;


import net.datastructures.LinkedPositionalList;
import net.datastructures.Position;

public class WordFix {
    public enum Type {
        None(0), Swap(1), Insert(2), Delete(3), Replace(4), Length(5);

        private final int val;
        private Type(int val) {
            this.val = val;
        }
        public int value() {
            return val;
        }
    }

    private Word origional;
    private LinkedPositionalList<Word> fixes = new LinkedPositionalList<>();
    protected int[] errorCounts = new int[] { 0, 0, 0, 0, 0 };

    public WordFix(Word origional) {
        this.origional = origional;
    }
    public int length() {
        return fixes.size();
    }
    public int count(Type type) {
        return errorCounts[type.value()];
    }
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
