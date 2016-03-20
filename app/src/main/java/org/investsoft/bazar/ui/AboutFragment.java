package org.investsoft.bazar.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.task.model.AsyncTaskResponse;
import org.investsoft.bazar.app.task.UpdateUserInfoTask;
import org.investsoft.bazar.ui.common.AsyncFragment;
import org.investsoft.bazar.ui.holder.UserInfoHolder;
import org.investsoft.bazar.api.model.base.User;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.UserConfig;
import org.investsoft.bazar.utils.events.EventManager;
import org.investsoft.bazar.utils.events.EventType;

public class AboutFragment extends AsyncFragment implements UpdateUserInfoTask.IUpdateUserInfoTaskCallback {

    private static AboutFragment instance = null;

    public static AboutFragment getInstance() {
        if (instance == null) {
            instance = new AboutFragment();
        }
        return instance;
    }

    private AboutFragment(){}

    private UserInfoHolder userInfoHolder;
    private User updatedUserInfo = null;
    private UpdateUserInfoTask updateUserInfoTask = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        if (userInfoHolder == null) {
            userInfoHolder = new UserInfoHolder(view);
        }
        fillUserInfoFields();

        Button updateInfoButton = (Button) view.findViewById(R.id.do_update_button);
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideKeyboard(view);
                attemptUpdate();
            }
        });

        return view;
    }

    private void attemptUpdate() {
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
            updatedUserInfo = new User(lastname, surname, name, email, phone);
            updateUserInfoTask = new UpdateUserInfoTask(updatedUserInfo, this);
            updateUserInfoTask.execute((Void) null);

        }
    }

    @Override
    public void getUpdateUserInfoTaskCallback(AsyncTaskResponse result) {
        showProgress(false);
        if (result.isSuccess()) {
            UserConfig.user = updatedUserInfo;
            UserConfig.save();
            EventManager.getInstance().sendEvent(EventType.USER_DATA_CHANGED);
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
        }
    }

    @Override
    protected String getProgressDialogMessage() {
        return getString(R.string.async_common);
    }

    public void fillUserInfoFields() {
        userInfoHolder.getNameView().setText(UserConfig.user.getName());
        userInfoHolder.getLastnameView().setText(UserConfig.user.getLastname());
        userInfoHolder.getSurnameView().setText(UserConfig.user.getSurname());
        userInfoHolder.getPhoneView().setText(UserConfig.user.getPhone());
        userInfoHolder.getEmailView().setText(UserConfig.user.getEmail());

    }

}
