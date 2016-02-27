package org.investsoft.bazar.action.common;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.api.model.put.UpdateUserInfoRequest;
import org.investsoft.bazar.api.model.put.UpdateUserInfoResponse;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 12.01.16.
 */
public class UpdateUserInfoTask extends AsyncTask<Void, Void, AsyncTaskResponse> {

    public interface IUpdateUserInfoTaskCallback {
        public void getUpdateUserInfoTaskCallback(AsyncTaskResponse result);
    }

    private final User user;

    private final IUpdateUserInfoTaskCallback caller;

    public UpdateUserInfoTask(User user, IUpdateUserInfoTaskCallback caller) {
        this.caller = caller;
        this.user = user;

    }

    @Override
    protected AsyncTaskResponse doInBackground(Void... params) {
        AsyncTaskResponse result = new AsyncTaskResponse();
        try {
            UpdateUserInfoRequest req = new UpdateUserInfoRequest(user, UserConfig.sessionId);
            UpdateUserInfoResponse resp = ApiClient.getInstance().updateUserInfo(req);
            if (resp.getCode() != 0) {
                throw new ApiException(resp.getMessage(), resp.getCode());
            }
            result.setSuccess(Boolean.TRUE);
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(AsyncTaskResponse result) {
        caller.getUpdateUserInfoTaskCallback(result);
    }
}
