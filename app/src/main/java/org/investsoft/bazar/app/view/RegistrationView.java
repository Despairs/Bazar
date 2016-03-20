package org.investsoft.bazar.app.view;

/**
 * Created by Despairs on 09.03.16.
 */
public interface RegistrationView extends AsyncView {

    public void navigateToLogin();

    public void resetErrors();

    public void setPasswordError();

    public void setEmailError(int errorType);

}
