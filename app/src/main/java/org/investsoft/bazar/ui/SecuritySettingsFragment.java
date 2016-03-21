package org.investsoft.bazar.ui;


import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.SwitchPreference;

import org.investsoft.bazar.R;
import org.investsoft.bazar.ui.common.BaseSettingFragment;
import org.investsoft.bazar.utils.UserConfig;

public class SecuritySettingsFragment extends BaseSettingFragment {

    private static int CREATE_PASSCODE = 0;

    private SwitchPreference passcodeEnabledPreference;
    private ListPreference autoLockInPreference;

    @Override
    protected void onSharedPreferenceChanged(String key) {
        switch (key) {
            case "passcodeEnabled":
                startActivityForResult(new Intent(getActivity(), PasscodeActivity.class), CREATE_PASSCODE);
                break;
        }
    }

    @Override
    protected int getPreferenceResourceId() {
        return R.xml.preferences_security;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passcodeEnabledPreference = (SwitchPreference) findPreference("passcodeEnabled");
        autoLockInPreference = (ListPreference) findPreference("autoLockIn");
        autoLockInPreference.setEnabled(passcodeEnabledPreference.isChecked());
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
