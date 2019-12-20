package com.github.eguanlao.flatfilemapper.handlers;

public class IntegerHandler implements Handler<Integer> {

    @Override
    public Integer handle(String value, Class<?> type, String pattern) {
        return Integer.valueOf(value);
    }

}
