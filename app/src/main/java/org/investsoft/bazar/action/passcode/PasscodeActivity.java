package org.investsoft.bazar.action.passcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.workflow.WorkflowActivity;
import org.investsoft.bazar.utils.AnimationUtils;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.SecurityUtils;
import org.investsoft.bazar.utils.SystemServiceHolder;
import org.investsoft.bazar.utils.UserConfig;

public class PasscodeActivity extends AppCompatActivity {

    private EditText passcodeView;
    private TextView titleView;

    private String newPasscode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        titleView = (TextView) findViewById(R.id.passcode_title);
        passcodeView = (EditText) findViewById(R.id.passcode);
        passcodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    if (TextUtils.isEmpty(UserConfig.passcodeHash)) {
                        if (TextUtils.isEmpty(newPasscode)) {
                            newPasscode = s.toString();
                            titleView.setText(R.string.passcode_confirm);
                            s.clear();
                        } else {
                            if (s.toString().equals(newPasscode)) {
                                UserConfig.passcodeHash = SecurityUtils.generatePasscodeHash(newPasscode, true);
                                UserConfig.save();
                                finish();
                            } else {
                                s.clear();
                                onError();
                            }
                        }
                    } else {
                        if (UserConfig.checkPasscode(s.toString())) {
                            UserConfig.appLocked = false;
                            UserConfig.save();
                            Intent i = new Intent(ApplicationLoader.applicationContext, WorkflowActivity.class);
                            i.putExtra("fromActivity", true);
                            startActivity(i);
                            finish();
                        } else {
                            s.clear();
                            onError();
                        }
                    }
                }
            }
        });
    }

    //Listener for virtual passcode keyboard
    public void onClick(View v) {
        passcodeView.setError(null);
        TextView tv = (TextView) v;
        AnimationUtils.changeTextColor(tv, Color.BLACK, Color.RED);
        if (tv.getText().length() == 1) {
            passcodeView.append(tv.getText());
        } else {
            int length = passcodeView.getText().length();
            if (length != 0) {
                passcodeView.getText().delete(length - 1, length);
            }
        }
        AnimationUtils.changeTextColor(tv, Color.RED, Color.BLACK);
    }

    public void onError() {
        if (SystemServiceHolder.vibrator != null) {
            SystemServiceHolder.vibrator.vibrate(200);
        }
        AnimationUtils.shakeView(titleView, 2, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (UserConfig.passcodeHash == null && UserConfig.passcodeEnabled) {
            UserConfig.passcodeEnabled = false;
            UserConfig.save();
        }
    }


}

