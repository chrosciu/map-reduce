package eu.chrost.mapreduce.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

public class SequentialMapReduce<I, K, V> implements MapReduce<I, K, V> {

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {
        Map<K, Collection<V>> results = new HashMap<>();

        while (input.hasNext()) {
            mapper.map(input.next(), (k, v) -> results.computeIfAbsent(k, __ -> new ArrayList<>()).add(v));
        }

        for (K key : results.keySet()) {
            reducer.reduce(key, results.get(key), output);
        }
    }

}
