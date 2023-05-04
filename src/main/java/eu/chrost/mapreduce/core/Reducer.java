package eu.chrost.mapreduce.core;

import java.util.function.BiConsumer;

public interface Reducer<K, V> {
    void reduce(K key, Iterable<V> values, BiConsumer<K, V> output);
}
