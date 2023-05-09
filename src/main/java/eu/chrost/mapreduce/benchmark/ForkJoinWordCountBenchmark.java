package eu.chrost.mapreduce.benchmark;

import eu.chrost.mapreduce.core.ForkJoinMapReduce;
import eu.chrost.mapreduce.core.MapReduce;
import eu.chrost.mapreduce.wordcount.BookInput;
import eu.chrost.mapreduce.wordcount.WordCountMapReduce;
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

import java.util.Map;

@State(Scope.Benchmark)
public class ForkJoinWordCountBenchmark {
    private MapReduce<String, String, Long> mapReduce;

    @Setup(Level.Iteration)
    public void setup() {
        mapReduce = new ForkJoinMapReduce<>();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    @Fork(1)
    @Measurement(iterations = 1)
    public void run(Blackhole blackhole) {
        Map<String, Long> map = WordCountMapReduce.run(mapReduce, BookInput.getInput());
        blackhole.consume(map);
    }

    @TearDown(Level.Iteration)
    public void teardown() {
        mapReduce.shutdown();
    }
}
