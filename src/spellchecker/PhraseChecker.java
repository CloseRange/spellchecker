package spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Iterates any phrase (some bunch of words) finding all mispelled words and correcting them
 */
public class PhraseChecker implements Iterable<WordFix> {
    private static final String CLEANER_REGEX = "[^a-zA-Z ]+";
    private static final String SPLITTER_REGEX = "\\s+";

    private int numChecked = 0;
    private int numMispelled = 0;
    private double avgSuggestions = 0;
    private int[] errorCounts = new int[] { 0, 0, 0, 0, 0 };

    private LinkedList<WordFix> fixes;

    public PhraseChecker(Dictionary dict, File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        StringBuilder phrase = new StringBuilder();
        while(scan.hasNextLine()) {
            phrase.append(scan.nextLine() + " ");
        }
        scan.close();
        processPhrase(dict, phrase.toString());
    }
    /**
     * parses a phrase into an array of single words.
     * @param phrase the phrase to parse
     * @return the array of words from the phrase
     */
    private String[] parsePhrase(String phrase) {
            phrase = phrase.replaceAll(CLEANER_REGEX, "");
        return phrase.split(SPLITTER_REGEX);
    }
    /**
     * Creates a new phraseChecker using a phrase and a dictionary to test against
     * @param dict the dictionary to test against
     * @param phrase the phrase to parse
     */
    public PhraseChecker(Dictionary dict, String phrase) {
        processPhrase(dict, phrase);
    }
    /**
     * processes a phrase using a dictionary and phrase, parsing through each word 
     * and testing if it needs correction.
     * @param dict the dictionary to test against
     * @param phrase the phrase to correct
     */
    private void processPhrase(Dictionary dict, String phrase) {
        String[] words = parsePhrase(phrase);
        fixes = new LinkedList<>();
        
        for(String string : words) {
            numChecked++;
            if(dict.isValid(string)) continue;
            numMispelled++;

            Word word = new Word(string);
            WordFix fix = SpellChecker.fix(dict, word);
            
            avgSuggestions += fix.length();
            fixes.add(fix);
            for(int j=1; j<WordFix.Type.Length.value(); j++) {
                errorCounts[j] += fix.errorCounts[j];
            }
        }
        avgSuggestions /= numMispelled;
    }
    /**
     * returns an iterator that iterates over all the mispelled words in the phrase.
     */
    @Override
    public Iterator<WordFix> iterator() {
        return fixes.iterator();
    }
    /**
     * logs basic stats about the corrected phrase.
     */
    public void logStats() {
        System.out.printf("# of words spellchecked: %d\n", numChecked);
        System.out.printf("%% of words mispelled: %.1f\n", 100*(numMispelled / (double) numChecked));
        System.out.printf("Average # of suggestions / misspelled word: %.1f\n", avgSuggestions);
        System.out.printf("Swaps: %d\n", errorCounts[WordFix.Type.Swap.value()]);
        System.out.printf("Insertions: %d\n", errorCounts[WordFix.Type.Insert.value()]);
        System.out.printf("Deletions: %d\n", errorCounts[WordFix.Type.Delete.value()]);
        System.out.printf("Replacements: %d\n", errorCounts[WordFix.Type.Replace.value()]);
    }
}
