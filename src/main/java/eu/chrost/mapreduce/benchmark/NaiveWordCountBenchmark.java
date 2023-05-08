package eu.chrost.mapreduce.benchmark;

import eu.chrost.mapreduce.wordcount.FileLineInput;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class NaiveWordCountBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    @Fork(1)
    @Measurement(iterations = 1)
    public void run(Blackhole blackhole) {
        HashMap<String, Long> map = new HashMap<>();
        Iterator<String> lines = new FileLineInput(getClass().getResourceAsStream("/book.txt"));
        while (lines.hasNext()) {
            String line = lines.next();
            StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                map.merge(str, 1L, Long::sum);
            }
        }
        blackhole.consume(map);
    }
}
