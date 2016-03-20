package org.investsoft.bazar.api.model.get;

import org.investsoft.bazar.api.model.base.ApiResponse;
import org.investsoft.bazar.api.model.base.User;

/**
 * Created by Despairs on 15.01.16.
 */
public class GetUserInfoResponse extends ApiResponse {

    private User result;

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

}

