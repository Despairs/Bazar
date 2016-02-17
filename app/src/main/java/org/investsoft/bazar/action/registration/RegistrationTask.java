package org.investsoft.bazar.action.registration;

import android.os.AsyncTask;

import org.investsoft.bazar.action.common.AsyncResult;
import org.investsoft.bazar.action.common.IActivityContext;
import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.post.RegistrationResponse;

/**
 * Created by Despairs on 12.01.16.
 */
public class RegistrationTask extends AsyncTask<Void, Void, AsyncResult> {

    public interface IRegCaller extends IActivityContext {
        public void processRegistrationResult(AsyncResult result);
    }

    private final String name;
    private final String lastname;
    private final String surname;
    private final String phone;
    private final String email;
    private final String password;
    private final IRegCaller caller;

    public RegistrationTask(String lastname, String name, String surname, String phone, String email, String password, IRegCaller caller) {
        this.caller = caller;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @Override
    protected AsyncResult doInBackground(Void... params) {
        AsyncResult result = new AsyncResult();
        ApiClient api = null;
        try {
            api = new ApiClient(caller.getContext());
            RegistrationResponse resp = api.registration(lastname, name, surname, phone, email, password);
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
    protected void onPostExecute(AsyncResult result) {
        caller.processRegistrationResult(result);
    }
}
