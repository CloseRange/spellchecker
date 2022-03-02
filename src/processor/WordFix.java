package processor;

public class WordFix {
    private String origional;
    private String[] fixes;

    public WordFix(String origional, String[] fixes) {
        this.origional = origional;
        this.fixes = fixes;
    }
    @Override
    public String toString() {
        if(fixes.length == 0) {
            return origional;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(origional + " - ");
        for(int i=0; i<fixes.length; i++) {
            if(i != 0) {
                builder.append(", ");
            }
            builder.append(fixes[i]);
        }
        return builder.toString();
    }
}
