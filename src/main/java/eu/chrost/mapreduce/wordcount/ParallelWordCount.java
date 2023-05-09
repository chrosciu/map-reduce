package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.ParallelMapReduce;

import java.util.Map;

public class ParallelWordCount {
    public static void main(String[] args) {
        MapReduce<String, String, Long> mapReduce = new ParallelMapReduce<>();
        Map<String, Long> map = WordCountMapReduce.run(mapReduce, BookInputStream.getBookInputStream());
        mapReduce.shutdown();
        System.out.println(map);
    }
}
