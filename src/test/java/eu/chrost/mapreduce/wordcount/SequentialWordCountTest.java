package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.SequentialMapReduce;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SequentialWordCountTest {
    @Test
    void shouldCountWords() {
        MapReduce<String, String, Long> mapReduce = new SequentialMapReduce<>();
        Map<String, Long> results = WordCountMapReduce.run(mapReduce, WikiInputStream.getWikiInputStream());
        mapReduce.shutdown();
        WordCountAssertions.checkWordCountResults(results);
    }
}
