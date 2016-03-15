package org.investsoft.bazar.app.task;

import android.os.AsyncTask;

import org.investsoft.bazar.api.ApiClient;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.post.RegistrationRequest;
import org.investsoft.bazar.app.task.model.AsyncTaskResponse;

/**
 * Created by Despairs on 12.01.16.
 */
public class RegistrationTask extends AsyncTask<Void, Void, AsyncTaskResponse> {

    public interface IRegistrationTaskCallback {
        public void getRegistartionTaskCallback(AsyncTaskResponse result);
    }

    private final String name;
    private final String lastname;
    private final String surname;
    private final String phone;
    private final String email;
    private final String password;
    private final IRegistrationTaskCallback caller;

    public RegistrationTask(String lastname, String name, String surname, String phone, String email, String password, IRegistrationTaskCallback caller) {
        this.caller = caller;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @Override
    protected AsyncTaskResponse doInBackground(Void... params) {
        AsyncTaskResponse result = new AsyncTaskResponse();
        try {
            RegistrationRequest req = new RegistrationRequest(lastname, name, surname, phone, email, password);
            ApiClient.getInstance().registration(req);
            result.setSuccess(Boolean.TRUE);
        } catch (ApiException e) {
            result.setSuccess(Boolean.FALSE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(AsyncTaskResponse result) {
        caller.getRegistartionTaskCallback(result);
    }
}
