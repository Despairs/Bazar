package org.investsoft.bazar.api.model.post;

import org.investsoft.bazar.api.model.base.HttpPostRequest;

/**
 * Created by Despairs on 15.01.16.
 */
public class RegistrationRequest extends HttpPostRequest {

    private String phone;
    private String name;
    private String lastname;
    private String surname;
    private String email;
    private String password;

    public RegistrationRequest(String lastname, String name, String surname, String phone, String email, String password) {
        this.phone = phone;
        this.name = name;
        this.setSurname(surname);
        this.setLastname(lastname);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
