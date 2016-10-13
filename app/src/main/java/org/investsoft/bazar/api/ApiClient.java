package org.investsoft.bazar.api;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.model.HttpRequest;
import org.investsoft.bazar.api.model.HttpRequestType;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.base.ApiResponse;
import org.investsoft.bazar.api.model.get.GetUserInfoRequest;
import org.investsoft.bazar.api.model.get.GetUserInfoResponse;
import org.investsoft.bazar.api.model.post.ChangePasswordRequest;
import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.api.model.post.LoginResponse;
import org.investsoft.bazar.api.model.post.RegistrationRequest;
import org.investsoft.bazar.api.model.put.UpdateUserInfoRequest;

/**
 * Created by EKovtunenko on 06.10.2016.
 */

public class ApiClient {

    private static ApiClient instance = new ApiClient();

    public static ApiClient getInstance() {
        return instance;
    }

    private ApiClient() {
    }

    public LoginResponse login(LoginRequest request) throws ApiException {
        return new ApiMethodExecuter<LoginRequest, LoginResponse>().execute(request, new LoginResponse());
    }

    public ApiResponse registration(RegistrationRequest request) throws ApiException {
        return new ApiMethodExecuter<RegistrationRequest, ApiResponse>().execute(request, new ApiResponse());
    }

    public GetUserInfoResponse getUserInfo(GetUserInfoRequest request) throws ApiException {
        return new ApiMethodExecuter<GetUserInfoRequest, GetUserInfoResponse>().execute(request, new GetUserInfoResponse());
    }

    public ApiResponse updateUserInfo(UpdateUserInfoRequest request) throws ApiException {
        return new ApiMethodExecuter<UpdateUserInfoRequest, ApiResponse>().execute(request, new ApiResponse());
    }

    public ApiResponse changePassword(ChangePasswordRequest request) throws ApiException {
        return new ApiMethodExecuter<ChangePasswordRequest, ApiResponse>().execute(request, new ApiResponse());
    }

}
