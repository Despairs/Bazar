package org.investsoft.bazar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
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
    public static String passcodeHash = null;
    public static byte[] passcodeSalt = new byte[0];
    public static boolean passcodeEnabled = false;
    public static boolean appLocked = false;

    public static void save() {
        SharedPreferences.Editor editor = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode).edit();
        editor.putString("sessionId", sessionId);
        editor.putBoolean("rememberMe", rememberMe);
        editor.putString("user", user != null ? user.toString() : null);
        editor.putString("passcodeHash", passcodeHash);
        editor.putString("passcodeSalt", passcodeSalt.length > 0 ? Base64.encodeToString(passcodeSalt, Base64.DEFAULT) : "");
        editor.putBoolean("appLocked", appLocked);
        editor.putBoolean("passcodeEnabled", passcodeEnabled);
        editor.commit();
    }

    public static void load() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode);
        sessionId = preferences.getString("sessionId", null);
        rememberMe = preferences.getBoolean("rememberMe", false);
        passcodeHash = preferences.getString("passcodeHash", "");
        appLocked = preferences.getBoolean("appLocked", false);
        passcodeEnabled = preferences.getBoolean("passcodeEnabled", false);
        user = JsonHelper.parseJson(preferences.getString("user", null), User.class);
        String passcodeSaltString = preferences.getString("passcodeSalt", "");
        if (passcodeSaltString.length() > 0) {
            passcodeSalt = Base64.decode(passcodeSaltString, Base64.DEFAULT);
        } else {
            passcodeSalt = new byte[0];
        }
        printAll(preferences);
    }

    public static void clearAll() {
        sessionId = null;
        user = null;
        rememberMe = false;
        passcodeHash = null;
        passcodeSalt = new byte[0];
        appLocked = false;
        save();
    }

    public static void clearPersonalInfo() {
        sessionId = null;
        user = null;
        save();
    }

    public static void clearPasscodeInfo() {
        appLocked = false;
        passcodeEnabled = false;
        passcodeHash = null;
        passcodeSalt = new byte[0];
        save();
    }

    public static void printAll(SharedPreferences preferences) {
        for (Map.Entry e : preferences.getAll().entrySet()) {
            Log.d("BAZAR", e.getKey() + " = " + e.getValue());
        }
    }

    public static boolean checkPasscode(String passcode) {
        if (passcodeSalt.length == 0) {
            boolean result = SecurityUtils.MD5(passcode).equals(passcodeHash);
            if (result) {
                passcodeHash = SecurityUtils.generatePasscodeHash(passcode, true);
                save();
            }
            return result;
        } else {
            String hash = SecurityUtils.generatePasscodeHash(passcode, false);
            return passcodeHash.equals(hash);

        }

    }
}
