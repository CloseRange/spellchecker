package spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import net.datastructures.LinkedPositionalList;

/**
 * Creates and stores a dictionary of valid words
 */
public class Dictionary implements Iterable<Word> {
    private LinkedPositionalList<Word> fullList;
    private HashMap<Integer, LinkedPositionalList<Word>> wordLists;
    private HashSet<String> existanceSet;

    /**
     * Create an empty dictionary
     */
    public Dictionary() {
        wordLists = new HashMap<>();
        existanceSet = new HashSet<>();
        fullList = new LinkedPositionalList<Word>();
    }
    /**
     * returns if the given word is valid
     * @param the word to test
     * @return if the given word is valid
     */
    public boolean isValid(Word word) {
        return isValid(word.toString());
    }
    /**
     * test if the given string is valid
     * @param word string to test
     * @return if the given string is valid
     */
    public boolean isValid(String word) {
        return existanceSet.contains(word);
    }
    /**
     * adds a string to the dictionary
     * @param string the string to add
     */
    public void push(String string) {
        push(new Word(string));
    }
    /**
     * adds a word to the dictionary
     * @param word the word to add
     */
    public void push(Word word) {
        if(existanceSet.contains(word.toString())) {
            return;
        }

        existanceSet.add(word.toString());
        LinkedPositionalList<Word> list = wordLists.get(word.length());

        if(list == null) {
            list = new LinkedPositionalList<>();
            wordLists.put(word.length(), list);
        }

        list.addLast(word);
        fullList.addLast(word);
    }
    /**
     * returns a list of all words in the dictionary with a particular length
     * @param letters the character count of words to retrieve 
     * @return the list of words. null if none exist
     */
    public LinkedPositionalList<Word> getList(int letters) {
        LinkedPositionalList<Word> wl = wordLists.get(letters);
        if(wl == null) {
            return null;
        }
        return wl;
    }
    /**
     * parses a dictionary file adding all words to the dictionary
     * @param filename the name of the dictionary file
     * @return the new dictionary
     * @throws FileNotFoundException if file doesn't exist
     */
    public static Dictionary parse(String filename) throws FileNotFoundException {
        Dictionary dict = new Dictionary();
        Scanner scan = new Scanner(new File(filename));
        while(scan.hasNextLine()) {
            dict.push(scan.nextLine());
        }
        scan.close();

        return dict;
    }
    /**
     * returns an iterator with all words in the dictionary
     * @return an iterator with all words in the dictionary
     */
    @Override
    public Iterator<Word> iterator() {
        return fullList.iterator();
    }
}
