package org.investsoft.bazar.action.registration;

import android.text.TextUtils;

import org.investsoft.bazar.action.common.AsyncTaskResponse;
import org.investsoft.bazar.action.common.BasePresenter;

/**
 * Created by Despairs on 09.03.16.
 */
public class RegistrationPresenter  extends BasePresenter<RegistrationView> implements RegistrationTask.IRegistrationTaskCallback {

    private RegistrationTask regTask = null;

    public void attemptRegistration(String lastname, String name, String surname, String phone, String email, String password) {
        if (regTask != null) {
            return;
        }
        // Reset errors.
        view.resetErrors();
        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            view.setPasswordError();
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            view.setEmailError(0);
            cancel = true;
        } else if (!email.contains("@")) {
            view.setEmailError(1);
            cancel = true;
        }
        if (!cancel) {
            view.showProgress(true);
            regTask = new RegistrationTask(lastname, name, surname, phone, email, password, this);
            regTask.execute((Void) null);
        }
    }

    @Override
    public void getRegistartionTaskCallback(AsyncTaskResponse result) {
        view.showProgress(false);
        if (result.isSuccess()) {
            view.navigateToLogin();
        } else {
            view.showErrorAlert(result.getMessage());
        }
        regTask = null;
    }
}
