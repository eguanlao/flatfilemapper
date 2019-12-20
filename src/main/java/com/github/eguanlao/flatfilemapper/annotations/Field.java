package com.github.eguanlao.flatfilemapper.annotations;

import com.github.eguanlao.flatfilemapper.handlers.Handler;
import com.github.eguanlao.flatfilemapper.handlers.NullHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

    int index();

    int length();

    boolean trim() default true;

    String pattern() default "";

    Class<? extends Handler<?>> handler() default NullHandler.class;

}
