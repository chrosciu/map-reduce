package eu.chrost.mapreduce.core;

import java.util.function.BiConsumer;

public interface Mapper<I, K, V> {
    void map(I in, BiConsumer<K, V> output);
}
