package com.tripalocal.bentuke.models.network;

/**
 * Created by naveen on 7/1/2015.
 */
public class LoginFBRequest {

    public String getToken() {
        return access_token;
    }

    public void string(String token) {
        this.access_token = token;
    }

    private String access_token;

    public LoginFBRequest(String token) {
        this.access_token = token;
    }
}
