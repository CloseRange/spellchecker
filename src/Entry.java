import processor.WordFix;

public class Entry {
    public static void main(String[] args) throws Exception {
        WordFix wf = new WordFix("Onec", new String[] {
            "Once", "One", "Ones", "dOnec", "nec"
        });
        System.out.println(wf);
    }
}
