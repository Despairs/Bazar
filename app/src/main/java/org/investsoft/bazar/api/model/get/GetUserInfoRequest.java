package org.investsoft.bazar.api.model.get;

import org.investsoft.bazar.api.model.base.HttpGetRequest;

/**
 * Created by Despairs on 15.01.16.
 */
public class GetUserInfoRequest extends HttpGetRequest {

    private String sessionId;
    private String userId;

    public GetUserInfoRequest(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUrlParameters() {
        return "?"
                .concat("sessionId=").concat(sessionId)
                .concat("&")
                .concat("userId=").concat(userId != null && !userId.isEmpty() ? userId : "");
    }
}
