package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.handlers.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    public static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";

    private static final Handler<Boolean> BOOLEAN_HANDLER = new BooleanHandler();
    private static final Handler<Date> DATE_HANDLER = new DateHandler();
    private static final Handler<Integer> INTEGER_HANDLER = new IntegerHandler();
    private static final Handler<LocalDate> LOCAL_DATE_HANDLER = new LocalDateHandler();
    private static final Handler<Long> LONG_HANDLER = new LongHandler();
    private static final Handler<Object> ENUM_HANDLER = new EnumHandler();

    private final Map<String, Handler<?>> handlerMap = new HashMap<>();

    public ObjectFactory(List<Class<? extends Handler<?>>> handlers) {
        loadHandlers();
        loadHandlers(handlers);
    }

    private void loadHandlers() {
        handlerMap.put("boolean", BOOLEAN_HANDLER);
        handlerMap.put("int", INTEGER_HANDLER);
        handlerMap.put("long", LONG_HANDLER);
        handlerMap.put(Boolean.class.getName(), BOOLEAN_HANDLER);
        handlerMap.put(Date.class.getName(), DATE_HANDLER);
        handlerMap.put(Integer.class.getName(), INTEGER_HANDLER);
        handlerMap.put(LocalDate.class.getName(), LOCAL_DATE_HANDLER);
        handlerMap.put(Long.class.getName(), LONG_HANDLER);
    }

    private void loadHandlers(List<Class<? extends Handler<?>>> handlers) {
        try {
            for (Class<? extends Handler<?>> handlerClass : handlers) {
                handlerMap.put(handlerClass.getName(), handlerClass.newInstance());
            }
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public Object create(Class<?> clazz, String line) {
        try {
            Object object = clazz.newInstance();
            for (Field field : FieldUtils.getFieldsListWithAnnotation(clazz, com.github.eguanlao.flatfilemapper.annotations.Field.class)) {
                FieldUtils.writeField(field, object, getFieldValue(field, line), true);
            }
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getFieldValue(Field field, String line) {
        com.github.eguanlao.flatfilemapper.annotations.Field fieldAnnotation = field.getAnnotation(com.github.eguanlao.flatfilemapper.annotations.Field.class);

        int index = fieldAnnotation.index();
        int length = fieldAnnotation.length();
        boolean trim = fieldAnnotation.trim();
        String pattern = fieldAnnotation.pattern();
        Class<? extends Handler<?>> handlerClass = fieldAnnotation.handler();

        String fieldValue = line.substring(index, index + length);
        if (trim) {
            fieldValue = fieldValue.trim();
        }

        String handlerMapKey = field.getType().getName();

        if (handlerMap.containsKey(handlerMapKey)) {
            Handler<?> handler = handlerMap.get(handlerMapKey);
            return handler.handle(fieldValue, field.getType(), pattern);
        }

        if (field.getType().isEnum()) {
            return ENUM_HANDLER.handle(fieldValue, field.getType(), pattern);
        }

        if (!handlerClass.equals(NullHandler.class)) {
            try {
                Object handlerObject = handlerMap.get(handlerClass.getName());
                return MethodUtils.invokeMethod(handlerObject, "handle", fieldValue, field.getType(), pattern);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return fieldValue;
    }

}
