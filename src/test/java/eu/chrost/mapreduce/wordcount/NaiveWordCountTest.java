package eu.chrost.mapreduce.wordcount;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class NaiveWordCountTest {
    @Test
    void shouldCountWords() {
        Map<String, Long> results = NaiveWordCount.run(WikiInputStream.getWikiInputStream());
        WordCountAssertions.checkWordCountResults(results);
    }
}
