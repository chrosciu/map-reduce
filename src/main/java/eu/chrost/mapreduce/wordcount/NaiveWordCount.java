package eu.chrost.mapreduce.wordcount;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NaiveWordCount {
    public static void main(String[] args) {
        Map<String, Long> map = run();
        System.out.println(map);
    }

    public static Map<String, Long> run() {
        HashMap<String, Long> map = new HashMap<>();
        Iterator<String> lines = new InputStreamLineIterator(BookInputStream.getBookInputStream());
        while (lines.hasNext()) {
            LineTokenizer.tokenizeLine(lines.next(), str -> map.merge(str, 1L, Long::sum));
        }
        return map;
    }
}
