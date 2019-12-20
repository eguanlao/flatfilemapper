package com.github.eguanlao.flatfilemapper.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.eguanlao.flatfilemapper.ObjectFactory.DEFAULT_DATE_PATTERN;

public class DateHandler implements Handler<Date> {

    @Override
    public Date handle(String value, Class<?> type, String pattern) {
        try {
            return new SimpleDateFormat(pattern.isEmpty() ? DEFAULT_DATE_PATTERN : pattern).parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
