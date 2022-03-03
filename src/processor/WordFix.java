package processor;

import java.util.ArrayList;

public class WordFix {
    private Word origional;
    private ArrayList<Word> fixes = new ArrayList<>();

    public WordFix(Word origional) {
        this.origional = origional;
    }
    public void push(Word word) {
        fixes.add(word);
    }

    @Override
    public String toString() {
        if(fixes.size() == 0) {
            return origional.toString();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(origional + " - ");
        for(int i=0; i<fixes.size(); i++) {
            if(i != 0) {
                builder.append(", ");
            }
            builder.append(fixes.get(i));
        }
        return builder.toString();
    }
}
