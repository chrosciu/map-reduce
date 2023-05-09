package eu.chrost.mapreduce.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

public class BatchingParallelMapReduce<I, K, V> implements MapReduce<I, K, V> {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);
    private final int batchSize;

    public BatchingParallelMapReduce(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {
        Queue<Map<K, List<V>>> results = new ConcurrentLinkedQueue<>();
        ArrayList<I> batch = new ArrayList<>(batchSize);
        long tasksCount = 0;

        while (input.hasNext()) {
            batch.add(input.next());
            if (batch.size() == batchSize || !input.hasNext()) {
                completionService.submit(new MapperTask<>(batch, mapper, results), (Void)null);
                ++tasksCount;
                batch = new ArrayList<>(batchSize);
            }
        }

        while (tasksCount > 0) {
            try {
                completionService.take();
                --tasksCount;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Map<K, List<V>> map = merge(results);

        for (K key : map.keySet()) {
            reducer.reduce(key, map.get(key), output);
        }
    }

    private static<K, V> Map<K, List<V>> merge(Queue<Map<K, List<V>>> mapResults) {
        return mapResults.parallelStream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(groupingByConcurrent(
                        Map.Entry::getKey,
                        mapping(
                                Map.Entry::getValue,
                                reducing(new ArrayList<>(), BatchingParallelMapReduce::concat))));
    }

    private static <V> List<V> concat(List<V> op1, List<V> op2) {
        ArrayList<V> result = new ArrayList<>(op1.size() + op2.size());
        result.addAll(op1);
        result.addAll(op2);
        return result;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    static final class MapperTask<I, K, V> implements Runnable {

        private final Iterable<I> input;
        private final Mapper<I, K, V> mapper;
        private final Queue<Map<K, List<V>>> results;

        MapperTask(Iterable<I> input, Mapper<I, K, V> mapper, Queue<Map<K, List<V>>> results) {
            this.input = input;
            this.mapper = mapper;
            this.results = results;
        }

        @Override
        public void run() {
            HashMap<K, List<V>> output = new HashMap<>();
            for(I item: input) {
                mapper.map(item, (k, v) -> output.computeIfAbsent(k, __ -> new ArrayList<>()).add(v));
            }
            results.offer(output);
        }
    }

}
