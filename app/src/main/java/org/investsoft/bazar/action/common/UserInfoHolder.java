package org.investsoft.bazar.action.common;

import android.view.View;
import android.widget.EditText;

import org.investsoft.bazar.R;

/**
 * Created by Despairs on 20.02.16.
 */
public class UserInfoHolder {

    private EditText nameView;
    private EditText lastnameView;
    private EditText surnameView;
    private EditText phoneView;
    private EditText emailView;
    private EditText passwordView;

    public UserInfoHolder(View view) {
        this.nameView = (EditText) view.findViewById(R.id.name);
        this.lastnameView = (EditText) view.findViewById(R.id.lastname);
        this.surnameView = (EditText) view.findViewById(R.id.surname);
        this.phoneView = (EditText) view.findViewById(R.id.phone);
        this.emailView = (EditText) view.findViewById(R.id.email);
        this.passwordView = (EditText) view.findViewById(R.id.password);
    }

    public EditText getNameView() {
        return nameView;
    }

    public void setNameView(EditText nameView) {
        this.nameView = nameView;
    }

    public EditText getLastnameView() {
        return lastnameView;
    }

    public void setLastnameView(EditText lastnameView) {
        this.lastnameView = lastnameView;
    }

    public EditText getSurnameView() {
        return surnameView;
    }

    public void setSurnameView(EditText surnameView) {
        this.surnameView = surnameView;
    }

    public EditText getPhoneView() {
        return phoneView;
    }

    public void setPhoneView(EditText phoneView) {
        this.phoneView = phoneView;
    }

    public EditText getEmailView() {
        return emailView;
    }

    public void setEmailView(EditText emailView) {
        this.emailView = emailView;
    }

    public EditText getPasswordView() {
        return passwordView;
    }

    public void setPasswordView(EditText passwordView) {
        this.passwordView = passwordView;
    }
}
