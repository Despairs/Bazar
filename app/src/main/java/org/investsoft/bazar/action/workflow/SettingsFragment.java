package org.investsoft.bazar.action.workflow;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.passcode.PasscodeActivity;
import org.investsoft.bazar.utils.UserConfig;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static int CREATE_PASSCODE = 0;

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
            SwitchPreferenceCompat passcodeEnabled = (SwitchPreferenceCompat) findPreference("passcodeEnabled");
            switch (resultCode) {
                case 0:
                    //If request cancelled - disable switch widget
                    UserConfig.passcodeEnabled = !passcodeEnabled.isChecked();
                    UserConfig.save();
                    passcodeEnabled.setChecked(!passcodeEnabled.isChecked());
                    break;
                case -1:
                    if (passcodeEnabled.isChecked()) {
                        UserConfig.load();
                    } else {
                        UserConfig.clearPasscodeInfo();
                        UserConfig.save();
                    }
                    break;
            }
        }
    }
}
