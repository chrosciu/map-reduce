package eu.chrost.mapreduce.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class ParallelMapReduce<I, K, V> implements MapReduce<I, K, V> {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {
        Map<K, Collection<V>> results = new ConcurrentHashMap<>();
        long tasksCount = 0;

        while (input.hasNext()) {
            completionService.submit(new MapperTask<>(input.next(), mapper, results), (Void)null);
            ++tasksCount;
        }

        while (tasksCount > 0) {
            try {
                completionService.take();
                --tasksCount;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (K key : results.keySet()) {
            reducer.reduce(key, results.get(key), output);
        }
    }

    static final class MapperTask<I, K, V> implements Runnable {
        private final I input;
        private final Mapper<I, K, V> mapper;
        private final Map<K, Collection<V>> results;

        MapperTask(I input, Mapper<I, K, V> mapper, Map<K, Collection<V>> results) {
            this.input = input;
            this.mapper = mapper;
            this.results = results;
        }

        @Override
        public void run() {
            mapper.map(input, (k, v) -> results.computeIfAbsent(k, __ -> new ConcurrentLinkedQueue<>()).add(v));
        }
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
