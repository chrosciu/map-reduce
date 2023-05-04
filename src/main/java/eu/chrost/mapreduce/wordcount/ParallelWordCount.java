package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.ParallelMapReduce;

import java.util.HashMap;

public class ParallelWordCount {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        MapReduce<String, String, Long> mapReduce = new ParallelMapReduce<>();
        mapReduce.run(
                new FileLineInput(ParallelWordCount.class.getResourceAsStream("/book.txt")),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        mapReduce.shutdown();
        System.out.println(map);
    }
}
