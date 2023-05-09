package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.SequentialMapReduce;

import java.util.Map;

public class SequentialWordCount {
    public static void main(String[] args) {
        MapReduce<String, String, Long> mapReduce = new SequentialMapReduce<>();
        Map<String, Long> map = WordCountMapReduce.run(mapReduce, BookInput.getInput());
        mapReduce.shutdown();
        System.out.println(map);
    }
}
