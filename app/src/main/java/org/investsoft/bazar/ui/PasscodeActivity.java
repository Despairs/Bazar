package org.investsoft.bazar.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.presenter.PasscodePresenter;
import org.investsoft.bazar.app.view.PasscodeView;
import org.investsoft.bazar.utils.AnimationUtils;
import org.investsoft.bazar.utils.SystemServiceHolder;
import org.investsoft.bazar.utils.UserConfig;

public class PasscodeActivity extends AppCompatActivity implements PasscodeView, TextWatcher {

    private EditText passcodeView;
    private TextView titleView;

    private ImageView logoutView;

    private PasscodePresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        if (presenter == null) {
            presenter = new PasscodePresenter();
        }

        titleView = (TextView) findViewById(R.id.passcode_title);
        passcodeView = (EditText) findViewById(R.id.passcode);
        passcodeView.addTextChangedListener(this);

        logoutView = (ImageView) findViewById(R.id.passcode_logout_button);

        boolean showLogout = UserConfig.passcodeEnabled && !TextUtils.isEmpty(UserConfig.passcodeHash);
        logoutView.setVisibility(showLogout ? View.VISIBLE : View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(!showLogout);
        }
    }

    //Listener for virtual passcode keyboard
    public void onNumberClick(View v) {
        passcodeView.setError(null);
        TextView tv = (TextView) v;
        AnimationUtils.changeTextColor(tv, Color.BLACK, Color.RED);
        passcodeView.append(tv.getText());
        AnimationUtils.changeTextColor(tv, Color.RED, Color.BLACK);
    }

    public void onImageClick(View v) {
        switch (v.getId()) {
            case R.id.passcode_clear_button:
                int length = passcodeView.getText().length();
                if (length != 0) {
                    passcodeView.getText().delete(length - 1, length);
                }
                break;
            case R.id.passcode_logout_button:
                presenter.onLogoutClick();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
        if (UserConfig.passcodeHash == null && UserConfig.passcodeEnabled) {
            UserConfig.passcodeEnabled = false;
            UserConfig.save();
        }
    }

    @Override
    public void setPasscodeConfirmTitle() {
        titleView.setText(R.string.passcode_confirm);
    }

    @Override
    public void showError() {
        if (SystemServiceHolder.vibrator != null) {
            SystemServiceHolder.vibrator.vibrate(200);
        }
        AnimationUtils.shakeView(titleView, 2, 0);
    }

    @Override
    public void navigateToWorkflow(boolean onlyFinish) {
        if (!onlyFinish) {
            startActivity(new Intent(this, WorkflowActivity.class));
        }
        setResult(RESULT_OK, null);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        presenter.checkPasscode(s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void showLogoutDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.dialog_logout_title));
            builder.setMessage(getString(R.string.dialog_logout));
            builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.onLogoutOkClick();
                }
            });
            builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
    }

}

