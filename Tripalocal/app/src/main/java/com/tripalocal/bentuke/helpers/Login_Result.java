package com.tripalocal.bentuke.helpers;

/**
 * Created by naveen on 4/26/2015.
 */
public class Login_Result {

    private String token;
    private String user_id;

    public Login_Result(String Token, String UserID) {
        this.token = Token;
        this.user_id = UserID;
    }

    public  String getToken() {
        return token;
    }

    public  void setToken(String token) {
        token = token;
    }

    public  String getUser_id() {
        return user_id;
    }

    public  void setUser_id(String user_id) {
        user_id = user_id;
    }
}
