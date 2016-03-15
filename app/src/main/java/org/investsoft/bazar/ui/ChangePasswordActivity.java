package org.investsoft.bazar.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.presenter.ChangePasswordPresenter;
import org.investsoft.bazar.app.view.ChangePasswordView;

/**
 * Created by Despairs on 13.03.16.
 */
public class ChangePasswordActivity extends AsyncActivity implements ChangePasswordView, View.OnClickListener {

    private ChangePasswordPresenter presenter = null;

    private EditText oldPasswordView;
    private EditText newPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (presenter == null) {
            presenter = new ChangePasswordPresenter();
        }

        // Set up the login form.
        oldPasswordView = (EditText) findViewById(R.id.change_password_old_password);
        newPasswordView = (EditText) findViewById(R.id.change_password_new_password);

        //Set listener to buttons
        findViewById(R.id.change_password_button).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void navigateToWorkflow() {
        finish();
    }

    @Override
    public void setPasswordError(int passwordType) {
        EditText view = null;
        switch (passwordType) {
            case 0:
                view = oldPasswordView;
                break;
            case 1:
                view = newPasswordView;
                break;
        }
        if (view != null) {
            view.setError(getString(R.string.error_field_required));
            view.requestFocus();
        }
    }

    @Override
    public void resetErrors() {
        oldPasswordView.setError(null);
        newPasswordView.setError(null);
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
    protected String getProgressDialogMessage() {
        return getString(R.string.async_common);
    }

    @Override
    public void onClick(View v) {
        presenter.attemptChangePassword(oldPasswordView.getText().toString(), newPasswordView.getText().toString());
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
