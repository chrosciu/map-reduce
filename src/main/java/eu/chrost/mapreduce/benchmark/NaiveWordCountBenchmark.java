package eu.chrost.mapreduce.benchmark;

import eu.chrost.mapreduce.wordcount.NaiveWordCount;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Map;

public class NaiveWordCountBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    @Fork(1)
    @Measurement(iterations = 1)
    public void run(Blackhole blackhole) {
        Map<String, Long> map = NaiveWordCount.run();
        blackhole.consume(map);
    }
}
