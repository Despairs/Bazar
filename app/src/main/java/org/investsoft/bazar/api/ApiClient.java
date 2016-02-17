package org.investsoft.bazar.api;

import android.content.Context;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.get.GetUserInfoRequest;
import org.investsoft.bazar.api.model.get.GetUserInfoResponse;
import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.api.model.post.LoginResponse;
import org.investsoft.bazar.api.model.post.RegistrationRequest;
import org.investsoft.bazar.api.model.post.RegistrationResponse;

/**
 * Created by Despairs on 15.01.16.
 */
public class ApiClient {

    private Context ctx;

    public ApiClient(Context ctx) throws ApiException {
        this.ctx = ctx;
    }

    public LoginResponse login(String email, String password) throws ApiException {
        return new ApiMethodExecuter<LoginRequest, LoginResponse>(ctx) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_login);
            }
        }.execute(new LoginRequest(email, password), new LoginResponse());
    }

    public RegistrationResponse registration(String lastname, String name, String surname, String phone, String email, String password) throws ApiException {
        return new ApiMethodExecuter<RegistrationRequest, RegistrationResponse>(ctx) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_user_action);
            }
        }.execute(new RegistrationRequest(lastname, name, surname, phone, email, password), new RegistrationResponse());
    }

    public GetUserInfoResponse getUserInfo(String sessionId, String userId) throws ApiException {
        return new ApiMethodExecuter<GetUserInfoRequest, GetUserInfoResponse>(ctx) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_user_action);
            }
        }.execute(new GetUserInfoRequest(sessionId, userId), new GetUserInfoResponse());
    }
}
