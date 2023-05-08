package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.Reducer;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.StreamSupport;

public final class WordCountReducer implements Reducer<String, Long> {

    @Override
    public void reduce(String key, Iterable<Long> values, BiConsumer<String, Long> output) {
        output.accept(key, StreamSupport.stream(values.spliterator(), false).mapToLong(l -> Optional.ofNullable(l).orElse(0L)).sum());
    }
}
