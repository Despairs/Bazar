package org.investsoft.bazar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.investsoft.bazar.api.model.base.User;

import java.util.Map;

/**
 * Created by Despairs on 27.02.16.
 */
public class UserConfig {

    public static final String cfgName = "org.investsoft.bazar.name";
    public static final int cfgMode = Context.MODE_PRIVATE;

    public static String sessionId = null;
    public static User user = null;
    public static boolean rememberMe = false;

    public static void save() {
        SharedPreferences.Editor editor = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode).edit();
        editor.putString("sessionId", sessionId);
        editor.putBoolean("rememberMe", rememberMe);
        editor.putString("user", user != null ? user.toString() : null);
        editor.commit();
    }

    public static void load() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode);
        sessionId = preferences.getString("sessionId", null);
        rememberMe = preferences.getBoolean("rememberMe", false);
        user = JsonHelper.parseJson(preferences.getString("user", null), User.class);
        printAll(preferences);
    }

    public static void clearAll() {
        sessionId = null;
        user = null;
        rememberMe = false;
        save();
    }

    public static void clearPersonalInfo() {
        sessionId = null;
        user = null;
        save();
    }

    public static void printAll(SharedPreferences preferences) {
        for (Map.Entry e : preferences.getAll().entrySet()) {
            Log.d("BAZAR", e.getKey() + " = " + e.getValue());
        }
    }
}
