package org.investsoft.bazar.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by EKovtunenko on 13.10.2016.
 */

public class StringUtils {
    public static String getHttpGetRequest(Object request) {
        StringBuilder sb = new StringBuilder("&");
        Class<?> clazz = request.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            if (!Modifier.isStatic(f.getModifiers())) {
                try {
                    f.setAccessible(true);
                    Object value = f.get(request);
                    sb.append(f.getName());
                    sb.append("=");
                    sb.append(value);
                    sb.append("?");
                } catch (IllegalArgumentException | IllegalAccessException ex) {

                }
            }
        }
        sb.deleteCharAt(sb.length());
        return sb.toString();
    }
}
