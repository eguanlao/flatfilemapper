package com.github.eguanlao.flatfilemapper.handlers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.eguanlao.flatfilemapper.ObjectFactory.DEFAULT_DATE_PATTERN;

public class LocalDateHandler implements Handler<LocalDate> {

    @Override
    public LocalDate handle(String value, Class<?> type, String pattern) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern.isEmpty() ? DEFAULT_DATE_PATTERN : pattern));
    }

}
