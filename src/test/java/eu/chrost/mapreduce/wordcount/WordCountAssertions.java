package eu.chrost.mapreduce.wordcount;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public final class WordCountAssertions {
    public static void checkWordCountResults(Map<String, Long> results) {
        List<Map.Entry<String, Long>> topEntries = results.entrySet().stream()
                .sorted((o1, o2) -> (int)(o2.getValue() - o1.getValue()))
                .limit(5)
                .toList();
        Assertions.assertEquals(topEntries.get(0), Map.entry("the", 38L));
        Assertions.assertEquals(topEntries.get(1), Map.entry("a", 28L));
        Assertions.assertEquals(topEntries.get(2), Map.entry("of", 25L));
        Assertions.assertEquals(topEntries.get(3), Map.entry("word", 24L));
        Assertions.assertEquals(topEntries.get(4), Map.entry("and", 23L));
    }
}
