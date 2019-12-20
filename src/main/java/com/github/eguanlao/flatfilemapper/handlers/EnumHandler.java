package com.github.eguanlao.flatfilemapper.handlers;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;

public class EnumHandler implements Handler<Object> {

    @Override
    public Object handle(String value, Class<?> type, String pattern) {
        try {
            return MethodUtils.invokeStaticMethod(type, "valueOf", value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
