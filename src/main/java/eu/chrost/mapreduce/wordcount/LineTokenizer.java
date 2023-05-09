package eu.chrost.mapreduce.wordcount;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class LineTokenizer {
    private static final Pattern PATTERN = Pattern.compile("\\s|\\p{Punct}");

    public static void tokenizeLine(String line, Consumer<String> tokenConsumer) {
        for (String token : PATTERN.split(line)) {
            tokenConsumer.accept(token);
        }
    }
}
