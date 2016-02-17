package org.investsoft.bazar.action.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.passcode.PasscodeActivity;

public class MainActivity extends AsyncActivity implements GetUserInfoTask.IGetUserInfoCaller {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private GetUserInfoTask userInfoTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btnClose = (Button) findViewById(R.id.btnClose);

        showProgress(true);
        userInfoTask = new GetUserInfoTask(null, this);
        userInfoTask.execute((Void) null);

        // Binding Click event to Button
//        final GetUserInfoTask.IGetUserInfoCaller caller = this;
//        btnClose.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//                userInfoTask = new GetUserInfoTask(null, caller);
//                userInfoTask.execute((Void) null);
//                finish();
//
//            }
//        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent i = new Intent(getApplicationContext(), PasscodeActivity.class);
        startActivity(i);
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_common);
    }

    @Override
    public void processGetUserInfoResult(GetUserInfoAsyncResult result) {
        showProgress(false);
        if (result.isSuccess()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("UserInfo")
                    .setMessage(result.getUserInfo().toString())
                    .setCancelable(false)
                    .setPositiveButton("Окей",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
//                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        userInfoTask = null;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}