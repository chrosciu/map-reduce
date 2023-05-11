package eu.chrost.mapreduce.wordcount;

import java.util.StringTokenizer;
import java.util.function.Consumer;

public final class LineTokenizer {

    public static void tokenizeLine(String line, Consumer<String> tokenConsumer) {
        StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
        while (tokenizer.hasMoreTokens()) {
            tokenConsumer.accept(tokenizer.nextToken());
        }
    }
}
