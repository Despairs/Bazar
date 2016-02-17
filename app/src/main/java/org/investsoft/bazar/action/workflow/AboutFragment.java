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

public class AboutFragment extends AsyncFragment implements GetUserInfoTask.IGetUserInfoCaller, UpdateUserInfoTask.IUpdateUserInfoCaller {

    // UI references.
    private EditText nameView;
    private EditText lastnameView;
    private EditText surnameView;
    private EditText phoneView;
    private EditText emailView;

    private GetUserInfoTask userInfoTask = null;
    private UpdateUserInfoTask updateUserInfoTask = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        //Init UI
        nameView = (EditText) view.findViewById(R.id.name);
        lastnameView = (EditText) view.findViewById(R.id.lastname);
        surnameView = (EditText) view.findViewById(R.id.surname);
        phoneView = (EditText) view.findViewById(R.id.phone);
        emailView = (EditText) view.findViewById(R.id.email);

        Button updateInfoButton = (Button) view.findViewById(R.id.do_update_button);
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });
        //Getting userInfo
        userInfoTask = new GetUserInfoTask(null, this);
        userInfoTask.execute((Void) null);
        return view;
    }

    private void attemptUpdate() {
        if (userInfoTask != null) {
            return;
        }
        // Reset errors.
        phoneView.setError(null);
        nameView.setError(null);
        lastnameView.setError(null);

        // Store values at the time of the registration attempt.
        String name = nameView.getText().toString();
        String lastname = lastnameView.getText().toString();
        String surname = surnameView.getText().toString();
        String phone = phoneView.getText().toString();
        String email = emailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            lastnameView.setError(getString(R.string.error_field_required));
            focusView = lastnameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
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
            nameView.setText(result.getUserInfo().getName());
            lastnameView.setText(result.getUserInfo().getLastname());
            surnameView.setText(result.getUserInfo().getSurname());
            phoneView.setText(result.getUserInfo().getPhone());
            emailView.setText(result.getUserInfo().getEmail());
        }
        userInfoTask = null;
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
            userInfoTask = null;
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
