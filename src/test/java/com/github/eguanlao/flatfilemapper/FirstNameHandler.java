package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.handlers.Handler;

public class FirstNameHandler implements Handler<FirstName> {

    @Override
    public FirstName handle(String value, Class<?> type, String pattern) {
        return new FirstName(value);
    }

}
