package eu.chrost.mapreduce.core;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class ParallelMapReduce<I, K, V> implements MapReduce<I, K, V> {

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {
        //TODO: Implement
    }


    @Override
    public void shutdown() {
        //TODO: Implement
    }
}
