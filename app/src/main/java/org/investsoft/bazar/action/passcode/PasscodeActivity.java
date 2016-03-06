package org.investsoft.bazar.action.passcode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.investsoft.bazar.R;
import org.investsoft.bazar.utils.AndroidUtils;

public class PasscodeActivity extends AppCompatActivity {

    private EditText passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
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
                    if (s.toString().equals("0000")) {
                        finish();
                    } else {
                        s.clear();
                        passcodeView.setError(getString(R.string.passcode_denied));
                    }
                }
            }
        });
    }

    //Listener for virtual passcode keyboard
    public void onClick(View v) {
        passcodeView.setError(null);
        TextView tv = (TextView) v;
        AndroidUtils.changeTextColorWithAnim(tv, Color.BLACK, Color.RED);
        if (tv.getText().length() == 1) {
            passcodeView.append(tv.getText());
        } else {
            int length = passcodeView.getText().length();
            if (length != 0) {
                passcodeView.getText().delete(length - 1, length);
            }
        }
        AndroidUtils.changeTextColorWithAnim(tv, Color.RED, Color.BLACK);
    }


}

