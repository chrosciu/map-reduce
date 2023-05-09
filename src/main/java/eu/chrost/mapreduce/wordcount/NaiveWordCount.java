package eu.chrost.mapreduce.wordcount;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NaiveWordCount {
    public static void main(String[] args) {
        Map<String, Long> map = run(BookInputStream.getBookInputStream());
        System.out.println(map);
    }

    public static Map<String, Long> run(InputStream inputStream) {
        HashMap<String, Long> map = new HashMap<>();
        Iterator<String> lines = new InputStreamLineIterator(inputStream);
        while (lines.hasNext()) {
            LineTokenizer.tokenizeLine(lines.next(), str -> map.merge(str, 1L, Long::sum));
        }
        return map;
    }
}
