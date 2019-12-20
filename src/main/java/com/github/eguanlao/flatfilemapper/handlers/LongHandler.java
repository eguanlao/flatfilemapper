package com.github.eguanlao.flatfilemapper.handlers;

public class LongHandler implements Handler<Long> {

    @Override
    public Long handle(String value, Class<?> type, String pattern) {
        return Long.valueOf(value);
    }

}
