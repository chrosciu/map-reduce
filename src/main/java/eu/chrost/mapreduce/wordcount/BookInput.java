package eu.chrost.mapreduce.wordcount;

import java.util.Iterator;

public final class BookInput {
    public static Iterator<String> getInput() {
        return new InputStreamLineIterator(BookInput.class.getResourceAsStream("/book.txt"));
    }
}
