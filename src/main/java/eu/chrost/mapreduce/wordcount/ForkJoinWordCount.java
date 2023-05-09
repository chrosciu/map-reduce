package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.ForkJoinMapReduce;
import eu.chrost.mapreduce.core.MapReduce;

import java.util.Map;

public class ForkJoinWordCount {
    public static void main(String[] args) {
        MapReduce<String, String, Long> mapReduce = new ForkJoinMapReduce<>();
        Map<String, Long> map = WordCountMapReduce.run(mapReduce, BookInputStream.getBookInputStream());
        mapReduce.shutdown();
        System.out.println(map);
    }
}
