package tripalocal.com.models.network;

import com.facebook.AccessToken;

/**
 * Created by naveen on 7/1/2015.
 */
public class LoginFBRequest {

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    private AccessToken token;

    public LoginFBRequest(AccessToken token) {
        this.token = token;
    }
}
