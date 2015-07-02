package com.tripalocal.bentuke.models.network;

/**
 * Created by naveen on 4/19/2015.
 */
public class Login_Request {
    private String email;
    private String password;

    public Login_Request(String Email, String pwd){
        this.email = Email;
        this.password = pwd;
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
}
