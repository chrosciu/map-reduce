package eu.chrost.mapreduce.benchmark;

import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.core.SequentialMapReduce;
import eu.chrost.mapreduce.wordcount.InputStreamLineIterator;
import eu.chrost.mapreduce.wordcount.WordCountMapper;
import eu.chrost.mapreduce.wordcount.WordCountReducer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;

@State(Scope.Benchmark)
public class SequentialWordCountBenchmark {
    private MapReduce<String, String, Long> mapReduce;

    @Setup(Level.Iteration)
    public void setup() {
        mapReduce = new SequentialMapReduce<>();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    @Fork(1)
    @Measurement(iterations = 1)
    public void run(Blackhole blackhole) {
        HashMap<String, Long> map = new HashMap<>();
        mapReduce.run(
                new InputStreamLineIterator(getClass().getResourceAsStream("/book.txt")),
                new WordCountMapper(),
                new WordCountReducer(),
                map::put);
        blackhole.consume(map);
    }

    @TearDown(Level.Iteration)
    public void teardown() {
        mapReduce.shutdown();
    }
}
