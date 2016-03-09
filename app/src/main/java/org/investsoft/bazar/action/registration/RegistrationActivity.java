package org.investsoft.bazar.action.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncActivity;
import org.investsoft.bazar.action.common.UserInfoHolder;
import org.investsoft.bazar.action.login.LoginActivity;

public class RegistrationActivity extends AsyncActivity implements View.OnClickListener, RegistrationView {


    private RegistrationPresenter presenter = null;

    private UserInfoHolder userInfoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenter();

        userInfoHolder = new UserInfoHolder(findViewById(R.id.content_registration));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.do_registration_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.attemptRegistration(
                userInfoHolder.getLastnameView().getText().toString(),
                userInfoHolder.getNameView().getText().toString(),
                userInfoHolder.getSurnameView().getText().toString(),
                userInfoHolder.getPhoneView().getText().toString(),
                userInfoHolder.getEmailView().getText().toString(),
                userInfoHolder.getPasswordView().getText().toString()
        );
    }

    @Override
    public void showErrorAlert(String message) {
        showAsyncTaskError(message);
    }

    @Override
    public void showProgress(boolean show) {
        showAsyncTaskProgress(show);
    }

    @Override
    public void navigateToLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("email", userInfoHolder.getEmailView().getText().toString());
        startActivity(i);
        finish();
    }

    @Override
    public void resetErrors() {
        userInfoHolder.getEmailView().setError(null);
        userInfoHolder.getPasswordView().setError(null);

    }

    @Override
    public void setPasswordError() {
        userInfoHolder.getPasswordView().requestFocus();
        userInfoHolder.getPasswordView().setError(getString(R.string.error_field_required));
    }

    @Override
    public void setEmailError(int errorType) {
        userInfoHolder.getEmailView().requestFocus();
        userInfoHolder.getEmailView().setError(errorType == 0 ? getString(R.string.error_field_required) : getString(R.string.error_invalid_email));
    }


    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_reg);
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
}
