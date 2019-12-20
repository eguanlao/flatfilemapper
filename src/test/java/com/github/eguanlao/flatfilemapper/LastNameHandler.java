package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.handlers.Handler;

public class LastNameHandler implements Handler<LastName> {

    @Override
    public LastName handle(String value, Class<?> type, String pattern) {
        return new LastName(value);
    }

}
