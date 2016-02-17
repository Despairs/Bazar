package org.investsoft.bazar.action.passcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.investsoft.bazar.R;


/**
 * A login screen that offers login via login/password.
 */

public class PasscodeActivity extends AppCompatActivity {

    // UI references.
    private EditText passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcodeView = (EditText) findViewById(R.id.passcode);
        PasscodeTextWatcher ptw = new PasscodeTextWatcher(this);
        passcodeView.addTextChangedListener(ptw);
    }

    private void attemptLogin() {

        // Reset errors.
        passcodeView.setError(null);

        // Store values at the time of the login attempt.
        String passcode = passcodeView.getText().toString();


    }

}

