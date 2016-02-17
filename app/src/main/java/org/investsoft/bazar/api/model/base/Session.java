package org.investsoft.bazar.api.model.base;

/**
 * Created by Despairs on 21.01.16.
 */
public class Session {

    private String sessionId;
    private User user;

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
