package org.investsoft.bazar.ui;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by Despairs on 19.01.16.
 */
public abstract class AsyncFragment extends Fragment {

    protected abstract String getProgressDialogMessage();

    private ProgressDialog progressDialog;

    protected void showProgress(boolean show) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
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
