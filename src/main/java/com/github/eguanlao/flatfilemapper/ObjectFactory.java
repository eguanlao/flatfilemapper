package com.github.eguanlao.flatfilemapper;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ObjectFactory {

    private static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";

    public static Object create(Class<?> clazz, String line) {
        try {
            Object object = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(com.github.eguanlao.flatfilemapper.annotations.Field.class)) {
                    Object fieldValue = createFieldValue(field, line);
                    field.set(object, fieldValue);
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object createFieldValue(Field field, String line) {
        com.github.eguanlao.flatfilemapper.annotations.Field fieldAnnotation = field.getAnnotation(com.github.eguanlao.flatfilemapper.annotations.Field.class);

        int index = fieldAnnotation.index();
        int length = fieldAnnotation.length();
        boolean trim = fieldAnnotation.trim();
        String pattern = fieldAnnotation.pattern();

        String fieldValue = line.substring(index, index + length);
        if (trim) {
            fieldValue = fieldValue.trim();
        }

        if (field.getType().isAssignableFrom(int.class)) {
            return Integer.parseInt(fieldValue);
        }

        if (field.getType().isAssignableFrom(long.class)) {
            return Long.parseLong(fieldValue);
        }

        if (field.getType().equals(LocalDate.class)) {
            return LocalDate.parse(fieldValue, DateTimeFormatter.ofPattern(pattern.isEmpty() ? DEFAULT_DATE_PATTERN : pattern));
        }

        return fieldValue;
    }

}
