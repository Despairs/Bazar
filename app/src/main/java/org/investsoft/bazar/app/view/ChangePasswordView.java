package org.investsoft.bazar.app.view;

/**
 * Created by Despairs on 09.03.16.
 */
public interface ChangePasswordView extends AsyncView {

    public void navigateToWorkflow();

    public void setPasswordError(int passwordType);

    public void resetErrors();

}
