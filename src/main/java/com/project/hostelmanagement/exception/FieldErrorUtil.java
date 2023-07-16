package com.project.hostelmanagement.exception;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

public class FieldErrorUtil {
    private FieldErrorUtil(){}

    public static String[] getJsonAliasValue(FieldError fieldError, BindingResult bindingResult) {
        String fieldName = fieldError.getField();
        Object targetObject = bindingResult.getTarget();

        Field field = targetObject != null ? getField(targetObject.getClass(), fieldName) : null;
        if (field != null && field.isAnnotationPresent(JsonAlias.class)) {
            JsonAlias jsonAlias = field.getAnnotation(JsonAlias.class);
            return jsonAlias.value();
        }
        return new String[]{fieldName};
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName);
            }
            return null;
        }
    }
}

