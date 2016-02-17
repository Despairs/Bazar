package org.investsoft.bazar.action.common;

import org.investsoft.bazar.api.model.base.User;

/**
 * Created by Despairs on 08.02.16.
 */
public class GetUserInfoAsyncResult extends AsyncResult {

    private User userInfo;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
