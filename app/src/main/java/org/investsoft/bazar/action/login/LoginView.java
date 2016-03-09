package org.investsoft.bazar.action.login;

import org.investsoft.bazar.action.common.AsyncView;

/**
 * Created by Despairs on 09.03.16.
 */
public interface LoginView extends AsyncView  {

    public void navigateToWorkflow();

    public void setPasswordError();

    public void setEmailError(int errorType);

    public void setEmail();

    public void resetErrors();

    public boolean isRememberMe();
}
