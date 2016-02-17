package org.investsoft.bazar.api.model.post;

import org.investsoft.bazar.api.model.base.BaseApiResponse;
import org.investsoft.bazar.api.model.base.Session;

/**
 * Created by Despairs on 15.01.16.
 */
public class LoginResponse extends BaseApiResponse {

    private Session result;

    public Session getResult() {
        return result;
    }

    public void setResult(Session result) {
        this.result = result;
    }

}

