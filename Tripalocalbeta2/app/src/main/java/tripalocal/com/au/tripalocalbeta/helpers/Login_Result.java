package tripalocal.com.au.tripalocalbeta.helpers;

/**
 * Created by naveen on 4/26/2015.
 */
public class Login_Result {

    private static String token;
    private static String user_id;

    public Login_Result(String Token, String UserID) {
        this.token = Token;
        this.user_id = UserID;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Login_Result.token = token;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        Login_Result.user_id = user_id;
    }
}
