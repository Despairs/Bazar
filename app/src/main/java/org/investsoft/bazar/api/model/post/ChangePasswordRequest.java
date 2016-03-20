package org.investsoft.bazar.api.model.post;

import org.investsoft.bazar.api.model.base.HttpPostRequest;

/**
 * Created by Despairs on 15.01.16.
 */
public class ChangePasswordRequest extends HttpPostRequest {

    private String sessionId;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest(String oldPassword, String newPassword, String sessionId) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
