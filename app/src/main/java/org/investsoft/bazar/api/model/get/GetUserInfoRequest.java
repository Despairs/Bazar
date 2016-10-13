package org.investsoft.bazar.api.model.get;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.model.HttpRequest;
import org.investsoft.bazar.api.model.HttpRequestType;

/**
 * Created by Despairs on 15.01.16.
 */
@HttpRequest(requestType= HttpRequestType.GET, methodId= R.string.api_user_action)
public class GetUserInfoRequest {

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

}
