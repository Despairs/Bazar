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
import org.investsoft.bazar.api.model.put.UpdateUserInfoRequest;
import org.investsoft.bazar.api.model.put.UpdateUserInfoResponse;
import org.investsoft.bazar.utils.ApplicationLoader;

/**
 * Created by Despairs on 15.01.16.
 */
public class ApiClient {

    private static ApiClient instance = new ApiClient();

    private final Context ctx;
    private final String url;

    public static ApiClient getInstance() {
        return instance;
    }

    private ApiClient() {
        ctx = ApplicationLoader.applicationContext;
        url = ApplicationLoader.applicationContext.getString(R.string.api_url);
    }

    public LoginResponse login(LoginRequest request) throws ApiException {
        return new ApiMethodExecuter<LoginRequest, LoginResponse>(url) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_login);
            }
        }.execute(request, new LoginResponse());
    }

    public RegistrationResponse registration(RegistrationRequest request) throws ApiException {
        return new ApiMethodExecuter<RegistrationRequest, RegistrationResponse>(url) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_user_action);
            }
        }.execute(request, new RegistrationResponse());
    }

    public GetUserInfoResponse getUserInfo(GetUserInfoRequest request) throws ApiException {
        return new ApiMethodExecuter<GetUserInfoRequest, GetUserInfoResponse>(url) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_user_action);
            }
        }.execute(request, new GetUserInfoResponse());
    }

    public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest request) throws ApiException {
        return new ApiMethodExecuter<UpdateUserInfoRequest, UpdateUserInfoResponse>(url) {

            @Override
            protected String getMethodPath() {
                return ctx.getString(R.string.api_user_action);
            }
        }.execute(request, new UpdateUserInfoResponse());
    }
}
