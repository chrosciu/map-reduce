package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.BatchingParallelMapReduce;
import eu.chrost.mapreduce.core.MapReduce;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BatchingParallelWordCountTest {
    @Test
    void shouldCountWords() {
        MapReduce<String, String, Long> mapReduce = new BatchingParallelMapReduce<>(5);
        Map<String, Long> results = WordCountMapReduce.run(mapReduce, WikiInputStream.getWikiInputStream());
        mapReduce.shutdown();
        WordCountAssertions.checkWordCountResults(results);
    }
}
