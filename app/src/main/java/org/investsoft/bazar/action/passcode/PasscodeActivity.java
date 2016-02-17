package org.investsoft.bazar.action.passcode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncActivity;
import org.investsoft.bazar.action.common.AsyncResult;
import org.investsoft.bazar.action.common.MainActivity;
import org.investsoft.bazar.action.login.AuthenticationTask;
import org.investsoft.bazar.action.registration.RegistrationActivity;


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

