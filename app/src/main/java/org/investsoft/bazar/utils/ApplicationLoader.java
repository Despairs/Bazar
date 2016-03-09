package org.investsoft.bazar.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Despairs on 26.02.16.
 */
public class ApplicationLoader extends Application {

    public static volatile Context applicationContext;
    private static volatile boolean applicationInited = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static void initApplication() {
        if (applicationInited) {
            return;
        }
        applicationInited = true;
        UserConfig.load();
//        UserConfig.passcodeHash = SecurityUtils.generatePasscodeHash("1234", true);
//        UserConfig.save();
        SystemServiceHolder.init();
    }
}
