package org.investsoft.bazar.action.workflow;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.passcode.PasscodeActivity;
import org.investsoft.bazar.utils.UserConfig;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName(UserConfig.cfgName);
        getPreferenceManager().setSharedPreferencesMode(UserConfig.cfgMode);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UserConfig.load();
        switch (key) {
            case "passcodeEnabled":
                if (sharedPreferences.getBoolean(key, false)) {
                    Intent i = new Intent(getActivity(), PasscodeActivity.class);
                    startActivity(i);
                } else {
                    UserConfig.clearPasscodeInfo();
                }
                break;
        }

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
