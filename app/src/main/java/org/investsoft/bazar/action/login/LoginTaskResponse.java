package org.investsoft.bazar.action.login;

import org.investsoft.bazar.action.common.AsyncTaskResponse;
import org.investsoft.bazar.api.model.base.Session;
import org.investsoft.bazar.api.model.post.LoginResponse;

/**
 * Created by Despairs on 08.02.16.
 */
public class LoginTaskResponse extends AsyncTaskResponse {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
