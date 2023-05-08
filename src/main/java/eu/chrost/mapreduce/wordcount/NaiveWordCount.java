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
            StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                map.merge(str, 1L, Long::sum);
            }
        }
        System.out.println(map);
    }
}
