package eu.chrost.mapreduce.wordcount;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NaiveWordCount {
    public static void main(String[] args) {
        Map<String, Long> map = run(BookInput.getInput());
        System.out.println(map);
    }

    public static Map<String, Long> run(Iterator<String> input) {
        HashMap<String, Long> map = new HashMap<>();
        //TODO: Implement
        return map;
    }
}
