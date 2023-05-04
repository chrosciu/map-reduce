package eu.chrost.mapreduce.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class FileLineInput implements Iterator<String> {

    private final BufferedReader reader;
    private String line;
    private boolean EOF;

    public FileLineInput(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
    }

    public FileLineInput(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public boolean hasNext() {

        if (EOF) {
            return false;
        }

        if (line == null) {
            try {
                line = reader.readLine();
                if (line == null) {
                    EOF = true;
                    return false;
                } else {
                    return true;
                }

            } catch (IOException e) {
                throw new IOError(e);
            }
        }

        return true;
    }

    @Override
    public String next() {
        if (hasNext()) {
            String next = line;
            line = null;
            return next;
        }
        throw new NoSuchElementException();
    }
}
