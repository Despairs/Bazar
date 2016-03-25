package org.investsoft.bazar.ui;

import android.preference.PreferenceActivity;
import android.view.MenuItem;

import org.investsoft.bazar.R;
import org.investsoft.bazar.ui.common.AppCompatPreferenceActivity;
import org.investsoft.bazar.utils.AndroidUtils;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onBackPressed();
        return true;
//        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AndroidUtils.needShowPasscode(false)) {
            finish();
        }
    }
}
