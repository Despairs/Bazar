package org.investsoft.bazar.action.passcode;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Despairs on 12.02.16.
 */
public class PasscodeTextWatcher implements TextWatcher {

    private final PasscodeActivity activity;

    public PasscodeTextWatcher(PasscodeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 4) {
            if (s.toString().equals("0000")) {
                activity.finish();
            }
        }
    }
}
