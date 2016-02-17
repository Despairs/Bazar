package org.investsoft.bazar.action.common;

import android.content.Context;
import android.os.AsyncTask;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.api.model.get.GetUserInfoResponse;
import org.investsoft.bazar.utils.JsonHelper;

/**
 * Created by Despairs on 12.01.16.
 */
public class GetUserInfoTask extends AsyncTask<Void, Void, GetUserInfoAsyncResult> {

    private final String userId;

    private final String cfg;
    private final String sessionIdKey;
    private final String userInfoKey;
    private final String sessionId;

    public interface IGetUserInfoCaller extends IActivityContext {
        public void processGetUserInfoResult(GetUserInfoAsyncResult result);
    }

    private final IGetUserInfoCaller caller;

    public GetUserInfoTask(String userId, IGetUserInfoCaller caller) {
        this.userId = userId;
        this.caller = caller;
        //Init config
        this.cfg = caller.getContext().getString(R.string.config);
        this.sessionIdKey = this.caller.getContext().getString(R.string.sessionId);
        this.userInfoKey = this.caller.getContext().getString(R.string.userInfo);
        //Restore sessionId
        this.sessionId = this.caller.getContext().getSharedPreferences(cfg, Context.MODE_PRIVATE).getString(sessionIdKey, null);
    }

    @Override
    protected GetUserInfoAsyncResult doInBackground(Void... params) {
        GetUserInfoAsyncResult result = new GetUserInfoAsyncResult();
        if (userId != null) {
            ApiClient api = null;
            try {
                api = new ApiClient(caller.getContext());
                if (sessionId != null) {
                    GetUserInfoResponse resp = api.getUserInfo(sessionId, userId);
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
            String userInfoJson = this.caller.getContext().getSharedPreferences(cfg, Context.MODE_PRIVATE).getString(userInfoKey, null);
            if (userInfoJson != null) {
                result.setUserInfo(JsonHelper.parseJson(userInfoJson, User.class));
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
