package org.investsoft.bazar.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by Despairs on 27.02.16.
 */
public class AndroidUtils {

    private static float density = 1;

    static {
        density = ApplicationLoader.applicationContext.getResources().getDisplayMetrics().density;
    }

    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        SystemServiceHolder.inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static boolean isKeyboardShowed(View view) {
        if (view == null) {
            return false;
        }
        return SystemServiceHolder.inputMethodManager.isActive(view);
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        if (!SystemServiceHolder.inputMethodManager.isActive()) {
            return;
        }
        SystemServiceHolder.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean needShowPasscode(boolean reset) {
        boolean wasInBackground;
        if (Build.VERSION.SDK_INT >= 14) {
            wasInBackground = ForegroundDetector.getInstance().isWasInBackground(reset);
            if (reset) {
                ForegroundDetector.getInstance().resetBackgroundVar();
            }
        } else {
            wasInBackground = UserConfig.lastPauseTime != 0;
        }
        return UserConfig.passcodeEnabled && wasInBackground &&
                (UserConfig.autoLockIn == 0 || (UserConfig.autoLockIn != 0 && UserConfig.lastPauseTime != 0
                        && (System.currentTimeMillis() - UserConfig.lastPauseTime) >= UserConfig.autoLockIn * 1000));
    }


    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (runnable == null) {
            return;
        }
        if (delay == 0) {
            ApplicationLoader.applicationHandler.post(runnable);
        } else {
            ApplicationLoader.applicationHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        ApplicationLoader.applicationHandler.removeCallbacks(runnable);
    }

}
