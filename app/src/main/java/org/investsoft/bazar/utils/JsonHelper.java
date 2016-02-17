package org.investsoft.bazar.utils;

import com.google.gson.Gson;

/**
 * Created by Despairs on 15.01.16.
 */
public class JsonHelper {

    private static final Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T extends Object> T parseJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
