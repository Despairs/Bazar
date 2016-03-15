package org.investsoft.bazar.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import org.investsoft.bazar.R;
import org.investsoft.bazar.utils.UserConfig;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static int CREATE_PASSCODE = 0;

    private SwitchPreferenceCompat passcodeEnabledPreference;
    private ListPreference autoLockInPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName(UserConfig.cfgName);
        getPreferenceManager().setSharedPreferencesMode(UserConfig.cfgMode);
        addPreferencesFromResource(R.xml.preferences);
        passcodeEnabledPreference = (SwitchPreferenceCompat) findPreference("passcodeEnabled");
        autoLockInPreference = (ListPreference) findPreference("autoLockIn");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UserConfig.load();
        switch (key) {
            case "passcodeEnabled":
                UserConfig.load();
                startActivityForResult(new Intent(getActivity(), PasscodeActivity.class), CREATE_PASSCODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_PASSCODE) {

            switch (resultCode) {
                case 0:
                    //If request cancelled - disable switch widget
                    UserConfig.passcodeEnabled = !passcodeEnabledPreference.isChecked();
                    UserConfig.save();
                    passcodeEnabledPreference.setChecked(!passcodeEnabledPreference.isChecked());
                    break;
                case -1:
                    autoLockInPreference.setEnabled(passcodeEnabledPreference.isChecked());
                    if (!passcodeEnabledPreference.isChecked()) {
                        UserConfig.clearPasscodeInfo();
                        UserConfig.save();
                    }
                    break;
            }
        }
    }
}
