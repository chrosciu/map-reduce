package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.MapReduce;

import java.util.HashMap;
import java.util.Map;

public final class WordCountMapReduce {
    public static Map<String, Long> run(MapReduce<String, String, Long> mapReduce) {
        HashMap<String, Long> map = new HashMap<>();
        mapReduce.run(
                new InputStreamLineIterator(BookInputStream.getBookInputStream()),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        return map;
    }
}
