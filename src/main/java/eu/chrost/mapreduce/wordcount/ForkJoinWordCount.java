package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.ForkJoinMapReduce;
import eu.chrost.mapreduce.core.MapReduce;

import java.util.HashMap;

public class ForkJoinWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        MapReduce<String, String, Long> mapReduce = new ForkJoinMapReduce<>();
        mapReduce.run(
                new InputStreamLineIterator(ForkJoinWordCount.class.getResourceAsStream("/book.txt")),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        mapReduce.shutdown();
        System.out.println(map);
    }
}
