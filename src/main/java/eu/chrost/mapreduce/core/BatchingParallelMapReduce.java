package eu.chrost.mapreduce.core;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class BatchingParallelMapReduce<I, K, V> implements MapReduce<I, K, V> {
    private final int batchSize;

    public BatchingParallelMapReduce(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {
        //TODO: Implement
    }


    @Override
    public void shutdown() {
        //TODO: Implement
    }
}
