package eu.chrost.mapreduce.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiConsumer;

public class ForkJoinMapReduce<I, K, V> implements MapReduce<I, K, V> {

    private static final int DEFAULT_BATCH_SIZE = 1000;

    private final ForkJoinPool pool = ForkJoinPool.commonPool();

    @Override
    public void run(Iterator<I> input, Mapper<I, K, V> mapper, Reducer<K, V> reducer, BiConsumer<K, V> output) {

        Map<K, List<V>> map = pool.invoke(new InputTask<I, K, V>(input, mapper));

        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            reducer.reduce(entry.getKey(), entry.getValue(), output);
        }
    }

    private final class InputTask<I, K, V> extends RecursiveTask<Map<K, List<V>>> {

        private static final long serialVersionUID = -8248428882481972328L;

        private final Iterator<I> in;
        private final Mapper<I, K, V> mapper;

        private InputTask(Iterator<I> in, Mapper<I, K, V> mapper) {
            this.in = in;
            this.mapper = mapper;
        }

        @Override
        protected Map<K, List<V>> compute() {
            List<I> batch = new ArrayList<>(DEFAULT_BATCH_SIZE);
            List<MapperTask<I, K, V>> tasks = new ArrayList<>();

            while (in.hasNext()) {
                batch.add(in.next());
                if (batch.size() == DEFAULT_BATCH_SIZE || !in.hasNext()) {
                    MapperTask<I, K, V> recursiveTask = new MapperTask<>(batch, mapper);
                    tasks.add(recursiveTask);
                    recursiveTask.fork();
                    batch = new ArrayList<>(DEFAULT_BATCH_SIZE);
                }
            }

            HashMap<K, List<V>> result = new HashMap<>();
            for (MapperTask<I, K, V> task : tasks) {
                Map<K, List<V>> results = task.join();
                for (Map.Entry<K, List<V>> entry : results.entrySet()) {
                    result.compute(entry.getKey(), (k, oldValue) -> {
                        if (oldValue == null) {
                            oldValue = new ArrayList<>();
                        }
                        oldValue.addAll(entry.getValue());
                        return oldValue;
                    });
                }
            }
            return result;
        }
    }

    private static final class MapperTask<I, K, V> extends RecursiveTask<Map<K, List<V>>> {

        private static final long serialVersionUID = -1462174441063624806L;

        private final List<I> batch;
        private final Mapper<I, K, V> mapper;

        private MapperTask(List<I> batch, Mapper<I, K, V> mapper) {
            this.batch = batch;
            this.mapper = mapper;
        }

        @Override
        protected Map<K, List<V>> compute() {
            HashMap<K, List<V>> map = new HashMap<>();
            for (I elem : batch) {
                mapper.map(
                        elem,
                        (k, v) -> map.compute(k, (key, oldValue) -> {
                            if (oldValue == null) {
                                oldValue = new ArrayList<>();
                            }
                            oldValue.add(v);
                            return oldValue;
                        }));
            }
            return map;
        }
    }

    @Override
    public void shutdown() {}
}
