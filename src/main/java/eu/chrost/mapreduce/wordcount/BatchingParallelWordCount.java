package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.BatchingParallelMapReduce;
import eu.chrost.mapreduce.core.MapReduce;

import java.util.HashMap;

public class BatchingParallelWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        MapReduce<String, String, Long> mapReduce = new BatchingParallelMapReduce<>();
        mapReduce.run(
                new InputStreamLineIterator(BatchingParallelWordCount.class.getResourceAsStream("/book.txt")),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        mapReduce.shutdown();
        System.out.println(map);
    }
}
