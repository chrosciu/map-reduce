package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.Reducer;

import java.util.function.BiConsumer;

public final class WordCountReducer implements Reducer<String, Long> {
    @Override
    public void reduce(String key, Iterable<Long> values, BiConsumer<String, Long> output) {
        //TODO: Implement
    }
}
