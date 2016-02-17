package org.investsoft.bazar.api.model.base;

/**
 * Created by Despairs on 21.01.16.
 */
public class User {

    private String email;
    private String password;
    private String name;
    private String lastname;
    private String surname;
    private String phone;

    public User() {

    }

    public User(String lastname, String surname, String name, String email, String phone) {
        this.lastname = lastname;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return "UserInfo: Lastname: " + lastname
                + "; Name: " + name
                + "; Surname: " + surname
                + "; Email: " + email
                + "; Phone: " + phone
                + "; Password: " + password;
    }
}
