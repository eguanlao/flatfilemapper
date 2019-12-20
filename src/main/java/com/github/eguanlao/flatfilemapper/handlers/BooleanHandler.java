package com.github.eguanlao.flatfilemapper.handlers;

public class BooleanHandler implements Handler<Boolean> {

    @Override
    public Boolean handle(String value, Class<?> type, String pattern) {
        return Boolean.valueOf(value);
    }

}
