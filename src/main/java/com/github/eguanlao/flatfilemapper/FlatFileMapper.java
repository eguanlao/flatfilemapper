package com.github.eguanlao.flatfilemapper;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Builder
public class FlatFileMapper {

    private Class<?> headerType;
    private Class<?> recordType;
    private Class<?> trailerType;

    public Object[] map(Reader reader) throws IOException {
        try (LineIterator lineIterator = IOUtils.lineIterator(reader)) {
            return mapInternal(lineIterator);
        }
    }

    public Object[] map(InputStream input, Charset encoding) throws IOException {
        try (LineIterator lineIterator = IOUtils.lineIterator(input, encoding)) {
            return mapInternal(lineIterator);
        }
    }

    public Object[] map(InputStream input, String encoding) throws IOException {
        try (LineIterator lineIterator = IOUtils.lineIterator(input, encoding)) {
            return mapInternal(lineIterator);
        }
    }

    public Object[] map(File file) throws IOException {
        try (LineIterator lineIterator = FileUtils.lineIterator(file)) {
            return mapInternal(lineIterator);
        }
    }

    public Object[] map(File file, String encoding) throws IOException {
        try (LineIterator lineIterator = FileUtils.lineIterator(file, encoding)) {
            return mapInternal(lineIterator);
        }
    }

    private Object[] mapInternal(LineIterator lineIterator) {
        Object header = null;
        List<Object> records = new ArrayList<>();
        Object trailer = null;

        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();

            if (isHeader(line)) {
                header = ObjectFactory.create(headerType, line);
            }
            if (isRecord(line)) {
                Object record = ObjectFactory.create(recordType, line);
                records.add(record);
            }
            if (isTrailer(line)) {
                trailer = ObjectFactory.create(trailerType, line);
            }
        }

        return new Object[]{header, records, trailer};
    }

    private boolean isHeader(String line) {
        return line.startsWith("H");
    }

    private boolean isRecord(String line) {
        return !isHeader(line) && !isTrailer(line);
    }

    private boolean isTrailer(String line) {
        return line.startsWith("T");
    }

}
