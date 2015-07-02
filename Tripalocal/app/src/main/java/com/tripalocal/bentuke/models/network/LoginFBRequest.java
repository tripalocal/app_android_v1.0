package com.tripalocal.bentuke.models.network;

import com.facebook.AccessToken;

/**
 * Created by naveen on 7/1/2015.
 */
public class LoginFBRequest {

    public AccessToken getToken() {
        return access_token;
    }

    public void setToken(AccessToken token) {
        this.access_token = token;
    }

    private AccessToken access_token;

    public LoginFBRequest(AccessToken token) {
        this.access_token = token;
    }
}
