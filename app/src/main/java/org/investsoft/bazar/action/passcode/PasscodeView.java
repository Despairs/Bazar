package org.investsoft.bazar.action.passcode;

/**
 * Created by Despairs on 10.03.16.
 */
public interface PasscodeView {

    public void setPasscodeConfirmTitle();

    public void showError();

    public void navigateToWorkflow(boolean onlyFinish);
}
