package org.investsoft.bazar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.investsoft.bazar.api.model.base.User;

/**
 * Created by Despairs on 27.02.16.
 */
public class UserConfig {

    private static final String cfg = "org.investsoft.bazar.cfg";

    public static String sessionId = null;
    public static User user = null;

    public static void save() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(cfg, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sessionId", sessionId);
        editor.putString("user", user != null ? user.toString() : null);
        editor.commit();
    }

    public static void load() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(cfg, Context.MODE_PRIVATE);
        sessionId = preferences.getString("sessionId", null);
        user = JsonHelper.parseJson(preferences.getString("user", null), User.class);
    }
}
