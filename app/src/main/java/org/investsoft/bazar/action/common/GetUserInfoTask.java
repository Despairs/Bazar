package org.investsoft.bazar.action.common;

import android.content.Context;
import android.os.AsyncTask;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.api.model.get.GetUserInfoRequest;
import org.investsoft.bazar.api.model.get.GetUserInfoResponse;
import org.investsoft.bazar.utils.JsonHelper;
import org.investsoft.bazar.utils.UserConfig;

/**
 * Created by Despairs on 12.01.16.
 */
public class GetUserInfoTask extends AsyncTask<Void, Void, GetUserInfoAsyncResult> {

    private final String userId;

    public interface IGetUserInfoCaller extends IActivityContext {
        public void processGetUserInfoResult(GetUserInfoAsyncResult result);
    }

    private final IGetUserInfoCaller caller;

    public GetUserInfoTask(String userId, IGetUserInfoCaller caller) {
        this.userId = userId;
        this.caller = caller;
    }

    @Override
    protected GetUserInfoAsyncResult doInBackground(Void... params) {
        GetUserInfoAsyncResult result = new GetUserInfoAsyncResult();
        if (userId != null) {
            ApiClient api = null;
            try {
                api = new ApiClient(caller.getContext());
                if (UserConfig.sessionId != null) {
                    GetUserInfoRequest req = new GetUserInfoRequest(UserConfig.sessionId , userId);
                    GetUserInfoResponse resp = api.getUserInfo(req);
                    if (resp.getCode() != 0) {
                        throw new ApiException(resp.getMessage(), resp.getCode());
                    }
                    result.setUserInfo(resp.getResult());
                    result.setSuccess(Boolean.TRUE);
                } else {
                    result.setSuccess(Boolean.FALSE);
                    result.setMessage("SessionId is null");
                }
            } catch (ApiException e) {
                result.setSuccess(Boolean.FALSE);
                result.setMessage(e.getMessage());
            }
        } else {
            if (UserConfig.user != null) {
                result.setUserInfo(UserConfig.user);
                result.setSuccess(Boolean.TRUE);
            } else {
                result.setMessage(caller.getContext().getString(R.string.error_invalid_user_info));
                result.setSuccess(Boolean.FALSE);
            }

        }
        return result;
    }

    @Override
    protected void onPostExecute(GetUserInfoAsyncResult result) {
        caller.processGetUserInfoResult(result);
    }


}
