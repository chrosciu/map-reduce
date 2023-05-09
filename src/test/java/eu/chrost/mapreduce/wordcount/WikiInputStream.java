package eu.chrost.mapreduce.wordcount;

import java.io.InputStream;

public final class WikiInputStream {
    public static InputStream getWikiInputStream() {
        return BookInputStream.class.getResourceAsStream("/wiki.txt");
    }
}
