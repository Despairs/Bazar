package org.investsoft.bazar.ui;


import org.investsoft.bazar.R;
import org.investsoft.bazar.ui.common.BaseSettingFragment;

public class AccountSettingsFragment extends BaseSettingFragment {

    @Override
    protected void onSharedPreferenceChanged(String key) {
    }

    @Override
    protected int getPreferenceResourceId() {
        return R.xml.preferences_account;
    }

}
