package eu.chrost.mapreduce.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

public class BatchingParallelMapReduce<I, K, V> implements MapReduce<I, K, V> {

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final int batchSize = 10000;
    private final int phaserMaxTasks = 1000;

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {

        Phaser rootPhaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase == 0 && registeredParties == 0 && !input.hasNext();
            }
        };

        // map
        int tasksPerPhaser = 0;
        Phaser phaser = new Phaser(rootPhaser);

        ConcurrentLinkedDeque<Map<K, List<V>>> mapResults = new ConcurrentLinkedDeque<>();
        ArrayList<I> batch = new ArrayList<>(batchSize);

        while (input.hasNext()) {
            batch.add(input.next());

            if (batch.size() == batchSize || !input.hasNext()) {
                phaser.register();

                executorService.submit(
                        new MapperPhase<>(batch.iterator(), mapper, mapResults, phaser));

                tasksPerPhaser++;
                if (tasksPerPhaser >= phaserMaxTasks) {
                    phaser = new Phaser(rootPhaser);
                    tasksPerPhaser = 0;
                }
                batch = new ArrayList<>(batchSize);
            }
        }

        rootPhaser.awaitAdvance(0);

        // merge map results
        Map<K, List<V>> map = merge(mapResults);

        // reduce
        reduce(reducer, output, map);
    }

    private void reduce(Reducer<K, V> reducer, BiConsumer<K, V> output, Map<K, List<V>> map) {
        Set<K> keys = map.keySet();
        for (K key : keys) {
            reducer.reduce(key, map.get(key), output);
        }
    }

    static <K, V> Map<K, List<V>> merge(ConcurrentLinkedDeque<Map<K, List<V>>> mapResults) {
        return mapResults.parallelStream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(groupingByConcurrent(
                        Map.Entry::getKey,
                        mapping(
                                Map.Entry::getValue,
                                reducing(new ArrayList<>(), BatchingParallelMapReduce::sum))));


//                .collect(groupingBy(
//                        Map.Entry::getKey,
//                        mapping(
//                                Map.Entry::getValue,
//                                reducing(new ArrayList<>(), BatchingParallelMapReduce::sum))));
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    static final class MapperPhase<I, K, V> implements Runnable {

        private final Iterator<I> input;
        private final Mapper<I, K, V> mapper;
        private final Phaser phaser;
        private final Queue<Map<K, List<V>>> mapResults;

        MapperPhase(Iterator<I> input, Mapper<I, K, V> mapper, Queue<Map<K, List<V>>> mapResults, Phaser phaser) {
            this.input = input;
            this.mapper = mapper;
            this.mapResults = mapResults;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            HashMap<K, List<V>> output = new HashMap<>();
            while (input.hasNext()) {
                mapper.map(input.next(), (k, v) -> output.computeIfAbsent(k, __ -> new ArrayList<>()).add(v));
            }
            mapResults.offer(output);
            phaser.arriveAndDeregister();
        }
    }

    private static <V> List<V> sum(List<V> op1, List<V> op2) {
        ArrayList<V> vs = new ArrayList<>(op1.size() + op2.size());
        vs.addAll(op1);
        vs.addAll(op2);
        return vs;
    }
}
