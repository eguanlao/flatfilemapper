package com.github.eguanlao.flatfilemapper.handlers;

public interface Handler<T> {

    T handle(String value, Class<?> type, String pattern);

}
