package com.github.eguanlao.flatfilemapper.handlers;

public class NullHandler implements Handler<Void> {

    @Override
    public Void handle(String value, Class<?> type, String pattern) {
        return null;
    }

}
