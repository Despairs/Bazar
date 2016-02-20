package org.investsoft.bazar.action.workflow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.AsyncFragment;
import org.investsoft.bazar.action.common.AsyncResult;
import org.investsoft.bazar.action.common.GetUserInfoAsyncResult;
import org.investsoft.bazar.action.common.GetUserInfoTask;
import org.investsoft.bazar.action.common.UpdateUserInfoTask;
import org.investsoft.bazar.action.common.UserInfoHolder;

public class AboutFragment extends AsyncFragment implements GetUserInfoTask.IGetUserInfoCaller, UpdateUserInfoTask.IUpdateUserInfoCaller {

    private UserInfoHolder userInfoHolder;

    private GetUserInfoTask getUserInfoTask = null;
    private UpdateUserInfoTask updateUserInfoTask = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        userInfoHolder = new UserInfoHolder(view);

        Button updateInfoButton = (Button) view.findViewById(R.id.do_update_button);
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });
        //Getting userInfo
        getUserInfoTask = new GetUserInfoTask(null, this);
        getUserInfoTask.execute((Void) null);
        return view;
    }

    private void attemptUpdate() {
        if (getUserInfoTask != null) {
            return;
        }
        // Reset errors.
        userInfoHolder.getEmailView().setError(null);
        userInfoHolder.getNameView().setError(null);
        userInfoHolder.getLastnameView().setError(null);

        // Store values at the time of the registration attempt.
        String name = userInfoHolder.getNameView().getText().toString();
        String lastname = userInfoHolder.getLastnameView().getText().toString();
        String surname = userInfoHolder.getSurnameView().getText().toString();
        String phone = userInfoHolder.getPhoneView().getText().toString();
        String email = userInfoHolder.getEmailView().getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            userInfoHolder.getNameView().setError(getString(R.string.error_field_required));
            focusView = userInfoHolder.getNameView();
            cancel = true;
        }
        if (TextUtils.isEmpty(lastname)) {
            userInfoHolder.getLastnameView().setError(getString(R.string.error_field_required));
            focusView = userInfoHolder.getLastnameView();
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            userInfoHolder.getPhoneView().setError(getString(R.string.error_field_required));
            focusView =  userInfoHolder.getPhoneView();
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            updateUserInfoTask = new UpdateUserInfoTask(lastname, name, surname, phone, email, this);
            updateUserInfoTask.execute((Void) null);

        }
    }

    @Override
    public void processGetUserInfoResult(GetUserInfoAsyncResult result) {
        if (result.isSuccess()) {
            userInfoHolder.getNameView().setText(result.getUserInfo().getName());
            userInfoHolder.getLastnameView().setText(result.getUserInfo().getLastname());
            userInfoHolder.getSurnameView().setText(result.getUserInfo().getSurname());
            userInfoHolder.getPhoneView().setText(result.getUserInfo().getPhone());
            userInfoHolder.getEmailView().setText(result.getUserInfo().getEmail());
        }
        getUserInfoTask = null;
    }

    @Override
    public void processUpdateUserInfoResult(AsyncResult result) {
        showProgress(false);
        if (result.isSuccess()) {
            Toast.makeText(getActivity(), getString(R.string.toast_update_user_info), Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Ошибка!")
                    .setMessage(result.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("Окей",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            getUserInfoTask = null;
        }
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_common);
    }

}
