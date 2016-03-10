package org.investsoft.bazar.action.login;

import android.text.TextUtils;

import org.investsoft.bazar.action.common.BasePresenter;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 09.03.16.
 */
public class LoginPresenter extends BasePresenter<LoginView> implements LoginTask.ILoginTaskCallback {

    private LoginTask loginTask = null;

    public void attemptLogin(String email, String password) {
        if (loginTask != null) {
            return;
        }
        // Reset errors.
        view.resetErrors();
        boolean error = false;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            view.setPasswordError();
            error = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            view.setEmailError(0);
            error = true;
        } else if (!email.contains("@")) {
            view.setEmailError(1);
            error = true;
        }
        if (!error) {
            view.showProgress(true);
            loginTask = new LoginTask(email, password, this);
            loginTask.execute((Void) null);

        }
    }

    @Override
    public void getLoginTaskCallback(LoginTaskResponse result) {
        view.showProgress(false);
        if (result.isSuccess()) {
            //Saving sessionId and userInfo
            UserConfig.sessionId = result.getSession().getSessionId();
            UserConfig.user = result.getSession().getUser();
            UserConfig.rememberMe = view.isRememberMe();
            UserConfig.save();
            view.navigateToWorkflow();
        } else {
            view.showErrorAlert(result.getMessage());
        }
        loginTask = null;
    }
}
