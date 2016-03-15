package org.investsoft.bazar.app.view;

/**
 * Created by Despairs on 09.03.16.
 */
public interface LoginView extends AsyncView  {

    public void navigateToWorkflow();

    public void setPasswordError();

    public void setEmailError(int errorType);

    public void resetErrors();

    public boolean isRememberMe();
}
