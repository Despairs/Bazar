package org.investsoft.bazar.app.presenter;

import android.text.TextUtils;

import org.investsoft.bazar.app.task.ChangePasswordTask;
import org.investsoft.bazar.app.task.LoginTask;
import org.investsoft.bazar.app.task.model.AsyncTaskResponse;
import org.investsoft.bazar.app.task.model.LoginTaskResponse;
import org.investsoft.bazar.app.view.ChangePasswordView;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 09.03.16.
 */
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> implements ChangePasswordTask.IChangePasswordTaskCallback {

    private ChangePasswordTask changePasswordTask = null;

    public void attemptChangePassword(String oldPassword, String newPassword) {
        if (changePasswordTask != null) {
            return;
        }
        // Reset errors.
        view.resetErrors();
        boolean error = false;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(oldPassword)) {
            view.setPasswordError(0);
            error = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(newPassword)) {
            view.setPasswordError(1);
            error = true;
        }
        if (!error) {
            view.showProgress(true);
            changePasswordTask = new ChangePasswordTask(oldPassword, newPassword, this);
            changePasswordTask.execute((Void) null);
        }
    }

    @Override
    public void getChangePasswordTaskCallback(AsyncTaskResponse result) {
        view.showProgress(false);
        if (result.isSuccess()) {
            view.navigateToWorkflow();
        } else {
            view.showErrorAlert(result.getMessage());
        }
        changePasswordTask = null;
    }
}
