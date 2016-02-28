package org.investsoft.bazar.action.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncActivity;
import org.investsoft.bazar.action.registration.RegistrationActivity;
import org.investsoft.bazar.action.workflow.WorkflowActivity;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.UserConfig;


/**
 * A login screen that offers login via login/password.
 */

public class LoginActivity extends AsyncActivity implements LoginTask.ILoginTaskCallback {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginTask authTask = null;

    // UI references.
    private EditText emailView;
    private EditText passwordView;
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        emailView = (EditText) findViewById(R.id.login_email);
        passwordView = (EditText) findViewById(R.id.login_password);
        rememberCheckBox = (CheckBox) findViewById(R.id.login_chkbx_rememberMe);

        //Trying to get stored data or data from Intent
        Intent i = getIntent();
        // Receiving the Data
        String email = i.getStringExtra("email");
        if (email != null && !email.isEmpty()) {
            emailView.setText(email);
        }

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicationLoader.applicationContext, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    private void attemptLogin() {
        if (authTask != null) {
            return;
        }
        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!email.contains("@")) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            authTask = new LoginTask(email, password, this);
            authTask.execute((Void) null);

        }
    }

    @Override
    public void getLoginTaskCallback(LoginTaskResponse response) {
        showProgress(false);
        if (response.isSuccess()) {
            //Saving sessionId and userInfo
            UserConfig.sessionId = response.getSession().getSessionId();
            UserConfig.user = response.getSession().getUser();
            UserConfig.rememberMe = rememberCheckBox.isChecked();
            UserConfig.save();
            Intent i = new Intent(this, WorkflowActivity.class);
            startActivity(i);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка!")
                    .setMessage(response.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("Окей",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        authTask = null;
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_login);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

