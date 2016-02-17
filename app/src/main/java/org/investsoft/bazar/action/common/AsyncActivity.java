package org.investsoft.bazar.action.common;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Despairs on 19.01.16.
 */
public abstract class AsyncActivity extends AppCompatActivity {

    protected abstract String getProgressDialogMessage();

    private ProgressDialog progressDialog;

    protected void showProgress(boolean show) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getProgressDialogMessage());
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
