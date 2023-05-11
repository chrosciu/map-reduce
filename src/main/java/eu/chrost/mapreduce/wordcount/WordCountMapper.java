package eu.chrost.mapreduce.wordcount;

import eu.chrost.mapreduce.core.Mapper;

import java.util.function.BiConsumer;

public final class WordCountMapper implements Mapper<String, String, Long> {
    @Override
    public void map(String in, BiConsumer<String, Long> output) {
        //TODO: Implement
    }
}
