package eu.chrost.mapreduce.core;

import java.util.Iterator;
import java.util.function.BiConsumer;

public interface MapReduce<I, K, V> {
    void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output);

    default void shutdown() {}
}
