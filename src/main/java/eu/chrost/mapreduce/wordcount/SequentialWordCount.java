package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.SequentialMapReduce;

import java.util.HashMap;

public class SequentialWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        MapReduce<String, String, Long> mapReduce = new SequentialMapReduce<>();
        mapReduce.run(
                new InputStreamLineIterator(BookInputStream.getBookInputStream()),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        mapReduce.shutdown();
        System.out.println(map);
    }
}
