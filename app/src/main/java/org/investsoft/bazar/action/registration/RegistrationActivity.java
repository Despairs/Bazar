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

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncActivity;
import org.investsoft.bazar.action.common.AsyncTaskResponse;
import org.investsoft.bazar.action.common.UserInfoHolder;
import org.investsoft.bazar.action.login.LoginActivity;

public class RegistrationActivity extends AsyncActivity implements RegistrationTask.IRegistrationTaskCallback {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RegistrationTask regTask = null;

    private UserInfoHolder userInfoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userInfoHolder = new UserInfoHolder(findViewById(R.id.content_registration));

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
        userInfoHolder.getEmailView().setError(null);
        userInfoHolder.getPasswordView().setError(null);

        // Store values at the time of the registration attempt.
        String name = userInfoHolder.getNameView().getText().toString();
        String lastname = userInfoHolder.getLastnameView().getText().toString();
        String surname = userInfoHolder.getSurnameView().getText().toString();
        String phone = userInfoHolder.getPhoneView().getText().toString();
        String email = userInfoHolder.getEmailView().getText().toString();
        String password = userInfoHolder.getPasswordView().getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            userInfoHolder.getPasswordView().setError(getString(R.string.error_field_required));
            focusView = userInfoHolder.getPasswordView();
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            userInfoHolder.getEmailView().setError(getString(R.string.error_field_required));
            focusView = userInfoHolder.getEmailView();
            cancel = true;
        } else if (!email.contains("@")) {
            userInfoHolder.getEmailView().setError(getString(R.string.error_invalid_email));
            focusView = userInfoHolder.getEmailView();
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
    public void getRegistartionTaskCallback(AsyncTaskResponse result) {
        showProgress(false);
        if (result.isSuccess()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.putExtra("email", userInfoHolder.getEmailView().getText().toString());
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

}
