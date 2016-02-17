package org.investsoft.bazar.action.common;

import android.content.Context;
import android.os.AsyncTask;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.api.model.post.LoginResponse;
import org.investsoft.bazar.api.model.post.RegistrationResponse;
import org.investsoft.bazar.api.model.put.UpdateUserInfoRequest;
import org.investsoft.bazar.api.model.put.UpdateUserInfoResponse;
import org.investsoft.bazar.utils.JsonHelper;

/**
 * Created by Despairs on 12.01.16.
 */
public class UpdateUserInfoTask extends AsyncTask<Void, Void, AsyncResult> {

    public interface IUpdateUserInfoCaller extends IActivityContext {
        public void processUpdateUserInfoResult(AsyncResult result);
    }

    private final String name;
    private final String lastname;
    private final String surname;
    private final String phone;
    private final String email;

    private final String cfg;
    private final String userInfoKey;
    private final String sessionIdKey;

    private final IUpdateUserInfoCaller caller;

    public UpdateUserInfoTask(String lastname, String name, String surname, String phone, String email, IUpdateUserInfoCaller caller) {
        this.caller = caller;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.phone = phone;
        this.email = email;

        //Init config
        this.cfg = caller.getContext().getString(R.string.config);
        this.sessionIdKey = this.caller.getContext().getString(R.string.sessionId);
        this.userInfoKey = this.caller.getContext().getString(R.string.userInfo);
    }

    @Override
    protected AsyncResult doInBackground(Void... params) {
        AsyncResult result = new AsyncResult();
        ApiClient api = null;
        try {
            api = new ApiClient(caller.getContext());
            User user = new User(lastname, name, surname, email, phone);
            //Restore sessionId
            String sessionId = this.caller.getContext().getSharedPreferences(cfg, Context.MODE_PRIVATE).getString(sessionIdKey, null);
            UpdateUserInfoRequest req = new UpdateUserInfoRequest(user, sessionId);
            UpdateUserInfoResponse resp = api.updateUserInfo(req);
            if (resp.getCode() != 0) {
                throw new ApiException(resp.getMessage(), resp.getCode());
            }
            result.setSuccess(Boolean.TRUE);
            //Updating userInfo
            caller.getContext().getSharedPreferences(cfg, Context.MODE_PRIVATE)
                    .edit()
                    .putString(userInfoKey, JsonHelper.toJson(user))
                    .commit();
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(AsyncResult result) {
        caller.processUpdateUserInfoResult(result);
    }


}
