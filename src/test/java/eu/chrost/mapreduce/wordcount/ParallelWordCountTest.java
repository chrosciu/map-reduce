package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.ParallelMapReduce;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ParallelWordCountTest {
    @Test
    void shouldCountWords() {
        MapReduce<String, String, Long> mapReduce = new ParallelMapReduce<>();
        Map<String, Long> results = WordCountMapReduce.run(mapReduce, WikiInputStream.getWikiInputStream());
        mapReduce.shutdown();
        WordCountAssertions.checkWordCountResults(results);
    }
}
