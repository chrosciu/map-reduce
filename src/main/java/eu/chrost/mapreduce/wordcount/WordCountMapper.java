package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.Mapper;

import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public final class WordCountMapper implements Mapper<String, String, Long> {

    private static final Pattern PATTERN = Pattern.compile("\\s|\\p{Punct}");

    @Override
    public void map(String in, BiConsumer<String, Long> output) {
        //for (String str : PATTERN.split(in.toLowerCase())) {
        StringTokenizer tokenizer = new StringTokenizer(in, " \t\n\r\f!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
        while (tokenizer.hasMoreTokens()) {
            String str = tokenizer.nextToken();
            output.accept(str, 1L);
        }
    }
}
