package org.investsoft.bazar.app.task;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.post.ChangePasswordRequest;
import org.investsoft.bazar.app.task.model.AsyncTaskResponse;
import org.investsoft.bazar.app.task.model.LoginTaskResponse;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 12.01.16.
 */
public class ChangePasswordTask extends AsyncTask<Void, Void, AsyncTaskResponse> {

    public interface IChangePasswordTaskCallback {
        public void getChangePasswordTaskCallback(AsyncTaskResponse result);
    }

    private final String oldPassword;
    private final String newPassword;
    private final IChangePasswordTaskCallback caller;

    public ChangePasswordTask(String oldPassword, String newPassword, IChangePasswordTaskCallback caller) {
        this.caller = caller;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    protected AsyncTaskResponse doInBackground(Void... params) {
        LoginTaskResponse result = new LoginTaskResponse();
        try {
            ChangePasswordRequest req = new ChangePasswordRequest(oldPassword, newPassword, UserConfig.sessionId);
            ApiClient.getInstance().changePassword(req);
            result.setSuccess(Boolean.TRUE);
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(AsyncTaskResponse result) {
        caller.getChangePasswordTaskCallback(result);
    }


}
