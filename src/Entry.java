import java.io.File;

import spellchecker.Dictionary;
import spellchecker.PhraseChecker;
import spellchecker.WordFix;

public class Entry {
    public static void main(String[] args) throws Exception {
        Dictionary d = Dictionary.parse("jlawler-wordlist.txt");
        
        PhraseChecker fixedData = new PhraseChecker(d, new File("document.txt"));

        for(WordFix fix : fixedData) {
            System.out.println(fix);
        }

        System.out.println();
        fixedData.logStats();
    }
}
