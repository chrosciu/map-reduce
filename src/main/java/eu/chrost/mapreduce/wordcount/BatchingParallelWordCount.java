package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.BatchingParallelMapReduce;
import eu.chrost.mapreduce.core.MapReduce;

import java.util.Map;

public class BatchingParallelWordCount {
    public static void main(String[] args) {
        MapReduce<String, String, Long> mapReduce = new BatchingParallelMapReduce<>(10000);
        Map<String, Long> map = WordCountMapReduce.run(mapReduce, BookInput.getInput());
        mapReduce.shutdown();
        System.out.println(map);
    }
}
