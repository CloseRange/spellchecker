package spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import net.datastructures.LinkedPositionalList;

public class Dictionary implements Iterable<Word> {
    private LinkedPositionalList<Word> fullList;
    private HashMap<Integer, LinkedPositionalList<Word>> wordLists;
    private HashSet<String> existanceSet;

    public Dictionary() {
        wordLists = new HashMap<>();
        existanceSet = new HashSet<>();
        fullList = new LinkedPositionalList<Word>();
    }
    public boolean isValid(Word word) {
        return isValid(word.toString());
    }
    public boolean isValid(String word) {
        return existanceSet.contains(word);
    }
    public void push(String string) {
        push(new Word(string));
    }
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
    
    public LinkedPositionalList<Word> getList(int letters) {
        LinkedPositionalList<Word> wl = wordLists.get(letters);
        if(wl == null) {
            return null;
        }
        return wl;
    }
    public static Dictionary parse(String filename) throws FileNotFoundException {
        Dictionary dict = new Dictionary();
        Scanner scan = new Scanner(new File(filename));
        while(scan.hasNextLine()) {
            dict.push(scan.nextLine());
        }
        scan.close();

        return dict;
    }
    @Override
    public Iterator<Word> iterator() {
        return fullList.iterator();
    }
}
