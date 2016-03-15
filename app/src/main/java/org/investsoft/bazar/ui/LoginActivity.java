package org.investsoft.bazar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.presenter.LoginPresenter;
import org.investsoft.bazar.app.view.LoginView;


/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AsyncActivity implements LoginView, OnClickListener {

    private static final int REG_ACTIVITY = 0;

    private LoginPresenter presenter = null;

    // UI references.
    private EditText emailView;
    private EditText passwordView;
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (presenter == null) {
            presenter = new LoginPresenter();
        }

        // Set up the login form.
        emailView = (EditText) findViewById(R.id.login_email);
        passwordView = (EditText) findViewById(R.id.login_password);
        rememberCheckBox = (CheckBox) findViewById(R.id.login_chkbx_rememberMe);

        //Set listener to buttons
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.registration_button).setOnClickListener(this);
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_login);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void showProgress(boolean show) {
        showAsyncTaskProgress(show);
    }

    @Override
    public void navigateToWorkflow() {
        startActivity(new Intent(this, WorkflowActivity.class));
        finish();
    }

    @Override
    public void setPasswordError() {
        passwordView.requestFocus();
        passwordView.setError(getString(R.string.error_field_required));
    }

    @Override
    public void setEmailError(int errorType) {
        emailView.requestFocus();
        emailView.setError(errorType == 0 ? getString(R.string.error_field_required) : getString(R.string.error_invalid_email));
    }

    @Override
    public void resetErrors() {
        passwordView.setError(null);
        emailView.setError(null);
    }

    @Override
    public void showErrorAlert(String message) {
        showAsyncTaskError(message);
    }

    @Override
    public boolean isRememberMe() {
        return rememberCheckBox.isChecked();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                presenter.attemptLogin(emailView.getText().toString(), passwordView.getText().toString());
                break;
            case R.id.registration_button:
                startActivityForResult(new Intent(this, RegistrationActivity.class), REG_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REG_ACTIVITY) {
            if (resultCode == RESULT_OK){
                String email = data.getStringExtra("email");
                if (email != null && !email.isEmpty()) {
                    emailView.setText(email);
                }
            }
        }
    }
}

