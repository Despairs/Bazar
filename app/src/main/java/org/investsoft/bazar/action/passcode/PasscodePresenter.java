package org.investsoft.bazar.action.passcode;

import android.text.Editable;
import android.text.TextUtils;

import org.investsoft.bazar.action.common.BasePresenter;
import org.investsoft.bazar.utils.SecurityUtils;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 10.03.16.
 */
public class PasscodePresenter extends BasePresenter<PasscodeView> {

    private String unconfirmedPasscode = null;

    public void checkPasscode(Editable s) {
        if (s.length() == 4) {
            //First attempt or not?
            if (TextUtils.isEmpty(UserConfig.passcodeHash)) {
                //Input first passcode (unconfirmed)
                if (TextUtils.isEmpty(unconfirmedPasscode)) {
                    unconfirmedPasscode = s.toString();
                    view.setPasscodeConfirmTitle();
                    s.clear();
                    return;
                }
                //Check unconfirmed passcode and confirmed passcode
                if (s.toString().equals(unconfirmedPasscode)) {
                    UserConfig.passcodeHash = SecurityUtils.generatePasscodeHash(unconfirmedPasscode, true);
                    UserConfig.save();
                    view.navigateToWorkflow(true);
                } else {
                    s.clear();
                    view.showError();
                }
            } else {
                if (UserConfig.checkPasscode(s.toString())) {
                    boolean onlyFinish = true;
                    if (UserConfig.appLocked) {
                        UserConfig.appLocked = false;
                        onlyFinish = false;
                        UserConfig.save();
                    }
                    view.navigateToWorkflow(onlyFinish);
                } else {
                    s.clear();
                    view.showError();
                }
            }
        }
    }


}
