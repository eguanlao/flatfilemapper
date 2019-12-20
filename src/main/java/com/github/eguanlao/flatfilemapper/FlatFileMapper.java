package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.handlers.Handler;
import lombok.Builder;
import lombok.Singular;
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

public class FlatFileMapper {

    private Class<?> headerType;
    private Class<?> recordType;
    private Class<?> trailerType;

    private ObjectFactory objectFactory;

    @Builder
    public FlatFileMapper(Class<?> headerType, Class<?> recordType, Class<?> trailerType, @Singular List<Class<? extends Handler<?>>> handlers) {
        this.headerType = headerType;
        this.recordType = recordType;
        this.trailerType = trailerType;
        this.objectFactory = new ObjectFactory(handlers);
    }

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
                header = objectFactory.create(headerType, line);
            }
            if (isRecord(line)) {
                Object record = objectFactory.create(recordType, line);
                records.add(record);
            }
            if (isTrailer(line)) {
                trailer = objectFactory.create(trailerType, line);
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
