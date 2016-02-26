package org.investsoft.bazar.action.login;

import android.content.Context;
import android.os.AsyncTask;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncResult;
import org.investsoft.bazar.action.common.IActivityContext;
import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.api.model.post.LoginResponse;
import org.investsoft.bazar.utils.JsonHelper;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 12.01.16.
 */
public class AuthenticationTask extends AsyncTask<Void, Void, AsyncResult> {

    public interface IAuthCaller extends IActivityContext {
        public void processAuthResult(AsyncResult result);
    }

    private final String email;
    private final String password;
    private final IAuthCaller caller;

    public AuthenticationTask(String email, String password, IAuthCaller caller) {
        this.caller = caller;
        this.email = email;
        this.password = password;
    }

    @Override
    protected AsyncResult doInBackground(Void... params) {
        AsyncResult result = new AsyncResult();
        ApiClient api = null;
        try {
            //@TODO Fake
            if (email.equals("error")) {
                throw new ApiException("Test error", 10);
            }
            api = new ApiClient(caller.getContext());
            LoginRequest req = new LoginRequest(email, password);
            LoginResponse resp = api.login(req);
            if (resp.getCode() != 0) {
                throw new ApiException(resp.getMessage(), resp.getCode());
            }
            result.setSuccess(Boolean.TRUE);
            //Saving sessionId and userInfo
            UserConfig.sessionId = resp.getResult().getSessionId();
            UserConfig.user = resp.getResult().getUser();
            UserConfig.save();
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(AsyncResult result) {
        caller.processAuthResult(result);
    }


}
