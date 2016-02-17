package org.investsoft.bazar.action.common;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
