package tripalocal.com.au.tripalocalbeta.models.network;

/**
 * Created by naveen on 6/21/2015.
 */
public class SignupRequest {

    private String email;
    private String password;
    private String first_name;

    public SignupRequest(String Email, String pwd, String fname, String lname){
        this.email = Email;
        this.password = pwd;
        this.first_name = fname;
        this.last_name = lname;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String last_name;
}
