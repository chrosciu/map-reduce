package eu.chrost.mapreduce.wordcount;

import java.util.Iterator;

public final class WikiInput {
    public static Iterator<String> getInput() {
        return new InputStreamLineIterator(WikiInput.class.getResourceAsStream("/wiki.txt"));
    }
}
