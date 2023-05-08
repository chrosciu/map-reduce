package eu.chrost.mapreduce.wordcount;

import java.util.HashMap;
import java.util.Iterator;

public class NaiveWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        Iterator<String> lines = new InputStreamLineIterator(NaiveWordCount.class.getResourceAsStream("/book.txt"));
        while (lines.hasNext()) {
            LineTokenizer.tokenizeLine(lines.next(), str -> map.merge(str, 1L, Long::sum));
        }
        System.out.println(map);
    }
}
