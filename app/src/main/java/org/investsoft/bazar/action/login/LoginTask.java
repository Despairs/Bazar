package org.investsoft.bazar.action.login;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.api.model.post.LoginResponse;

/**
 * Created by Despairs on 12.01.16.
 */
public class LoginTask extends AsyncTask<Void, Void, LoginTaskResponse> {

    public interface ILoginTaskCallback {
        public void getLoginTaskCallback(LoginTaskResponse result);
    }

    private final String email;
    private final String password;
    private final ILoginTaskCallback caller;

    public LoginTask(String email, String password, ILoginTaskCallback caller) {
        this.caller = caller;
        this.email = email;
        this.password = password;
    }

    @Override
    protected LoginTaskResponse doInBackground(Void... params) {
        LoginTaskResponse result = new LoginTaskResponse();
        try {
            //@TODO Fake
            if (email.equals("error")) {
                throw new ApiException("Test error", 10);
            }
            LoginRequest req = new LoginRequest(email, password);
            LoginResponse resp = ApiClient.getInstance().login(req);
            if (resp.getCode() != 0) {
                throw new ApiException(resp.getMessage(), resp.getCode());
            }
            result.setSuccess(Boolean.TRUE);
            result.setSession(resp.getResult());
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(LoginTaskResponse result) {
        caller.getLoginTaskCallback(result);
    }


}
