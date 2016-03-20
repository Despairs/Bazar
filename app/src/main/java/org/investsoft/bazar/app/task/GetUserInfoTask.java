package org.investsoft.bazar.app.task;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.get.GetUserInfoRequest;
import org.investsoft.bazar.api.model.get.GetUserInfoResponse;
import org.investsoft.bazar.app.task.model.GetUserInfoTaskResponse;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 12.01.16.
 */
public class GetUserInfoTask extends AsyncTask<Void, Void, GetUserInfoTaskResponse> {

    private final String userId;

    public interface IGetUserInfoTaskCallback {
        public void getUserInfoTaskCallback(GetUserInfoTaskResponse result);
    }

    private final IGetUserInfoTaskCallback caller;

    public GetUserInfoTask(String userId, IGetUserInfoTaskCallback caller) {
        this.userId = userId;
        this.caller = caller;
    }

    @Override
    protected GetUserInfoTaskResponse doInBackground(Void... params) {
        GetUserInfoTaskResponse result = new GetUserInfoTaskResponse();
        try {
            if (UserConfig.sessionId != null) {
                GetUserInfoRequest req = new GetUserInfoRequest(UserConfig.sessionId, userId);
                GetUserInfoResponse resp = ApiClient.getInstance().getUserInfo(req);
                result.setUser(resp.getResult());
                result.setSuccess(Boolean.TRUE);
            } else {
                result.setSuccess(Boolean.FALSE);
                result.setMessage("SessionId is null");
            }
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(GetUserInfoTaskResponse result) {
        caller.getUserInfoTaskCallback(result);
    }

}
