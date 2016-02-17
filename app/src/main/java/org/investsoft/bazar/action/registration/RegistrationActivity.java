package org.investsoft.bazar.action.registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncActivity;
import org.investsoft.bazar.action.common.AsyncResult;
import org.investsoft.bazar.action.login.LoginActivity;

public class RegistrationActivity extends AsyncActivity implements RegistrationTask.IRegCaller {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RegistrationTask regTask = null;

    // UI references.
    private EditText nameView;
    private EditText lastnameView;
    private EditText surnameView;
    private EditText phoneView;
    private EditText emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the login form.
        nameView = (EditText) findViewById(R.id.name);
        lastnameView = (EditText) findViewById(R.id.lastname);
        surnameView = (EditText) findViewById(R.id.surname);
        phoneView = (EditText) findViewById(R.id.phone);
        emailView = (EditText) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.reg_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button signInButton = (Button) findViewById(R.id.do_registration_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_reg);
    }

    private void attemptRegistration() {
        if (regTask != null) {
            return;
        }
        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the registration attempt.
        String name = nameView.getText().toString();
        String lastname = lastnameView.getText().toString();
        String surname = surnameView.getText().toString();
        String phone = phoneView.getText().toString();
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
            regTask = new RegistrationTask(lastname, name, surname, phone, email, password, this);
            regTask.execute((Void) null);

        }
    }

    @Override
    public void processRegistrationResult(AsyncResult result) {
        showProgress(false);
        if (result.isSuccess()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.putExtra("email", emailView.getText().toString());
            startActivity(i);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка!")
                    .setMessage(result.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("Окей",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            regTask = null;
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
