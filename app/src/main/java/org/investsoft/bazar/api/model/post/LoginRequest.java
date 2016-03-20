package org.investsoft.bazar.api.model.post;

import org.investsoft.bazar.api.model.base.HttpPostRequest;

/**
 * Created by Despairs on 15.01.16.
 */
public class LoginRequest extends HttpPostRequest {

    private String login;
    private String password;

    public LoginRequest (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
