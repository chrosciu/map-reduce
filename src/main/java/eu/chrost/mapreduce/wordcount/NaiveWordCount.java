package eu.chrost.mapreduce.wordcount;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class NaiveWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        Iterator<String> lines = new FileLineInput(NaiveWordCount.class.getResourceAsStream("/book.txt"));
        while (lines.hasNext()) {
            String line = lines.next();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                Long count = map.get(str);
                if (count != null) {
                    map.put(str, count + 1);
                } else {
                    map.put(str, 1L);
                }
            }
        }
        System.out.println(map);
    }
}
