package eu.chrost.mapreduce.wordcount;

import java.io.InputStream;

public final class BookInputStream {
    public static InputStream getBookInputStream() {
        return BookInputStream.class.getResourceAsStream("/book.txt");
    }
}
