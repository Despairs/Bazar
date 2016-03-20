package org.investsoft.bazar.app.task.model;

import org.investsoft.bazar.api.model.base.User;

/**
 * Created by Despairs on 08.02.16.
 */
public class GetUserInfoTaskResponse extends AsyncTaskResponse {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
