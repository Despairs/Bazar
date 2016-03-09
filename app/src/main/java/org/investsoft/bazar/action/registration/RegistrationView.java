package org.investsoft.bazar.action.registration;

import org.investsoft.bazar.action.common.AsyncView;

/**
 * Created by Despairs on 09.03.16.
 */
public interface RegistrationView extends AsyncView {

    public void navigateToLogin();

    public void resetErrors();

    public void setPasswordError();

    public void setEmailError(int errorType);

}
