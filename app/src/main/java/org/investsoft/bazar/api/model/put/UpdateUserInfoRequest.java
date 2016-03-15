package org.investsoft.bazar.api.model.put;

import org.investsoft.bazar.api.model.base.HttpPutRequest;
import org.investsoft.bazar.api.model.base.User;

/**
 * Created by Despairs on 15.01.16.
 */
public class UpdateUserInfoRequest extends HttpPutRequest {

    private String sessionId;
    private User user;

    public UpdateUserInfoRequest(User user, String sessionId) {
        this.setUser(user);
        this.setSessionId(sessionId);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
