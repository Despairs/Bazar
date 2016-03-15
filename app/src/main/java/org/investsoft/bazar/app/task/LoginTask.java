package org.investsoft.bazar.app.task;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.api.model.post.LoginResponse;
import org.investsoft.bazar.app.task.model.LoginTaskResponse;

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
            LoginRequest req = new LoginRequest(email, password);
            LoginResponse resp = ApiClient.getInstance().login(req);
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
