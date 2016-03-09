package org.investsoft.bazar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Despairs on 06.03.16.
 */
public class SystemServiceHolder {

    public static Vibrator vibrator;
    public static ConnectivityManager connectivityManager;
    public static InputMethodManager inputMethodManager;

    public static void init() {
        vibrator = (Vibrator) ApplicationLoader.applicationContext.getSystemService(Context.VIBRATOR_SERVICE);
        connectivityManager = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        inputMethodManager = (InputMethodManager) ApplicationLoader.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
