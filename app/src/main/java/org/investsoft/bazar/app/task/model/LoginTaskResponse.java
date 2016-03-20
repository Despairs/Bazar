package org.investsoft.bazar.app.task.model;

import org.investsoft.bazar.api.model.base.Session;

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
