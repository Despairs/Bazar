package org.investsoft.bazar.ui.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.investsoft.bazar.R;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 17.03.16.
 */
public abstract class BaseSettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    protected abstract void onSharedPreferenceChanged(String key);

    protected abstract int getPreferenceResourceId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(UserConfig.cfgName);
        getPreferenceManager().setSharedPreferencesMode(UserConfig.cfgMode);
        addPreferencesFromResource(getPreferenceResourceId());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UserConfig.load();
        onSharedPreferenceChanged(key);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
