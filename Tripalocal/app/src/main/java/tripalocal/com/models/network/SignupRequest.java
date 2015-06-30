package tripalocal.com.models.network;

/**
 * Created by naveen on 6/21/2015.
 */
public class SignupRequest {

    private String email;
    private String password;
    private String first_name;
    private String phone_number;
    private String last_name;

    public SignupRequest(String Email, String pwd, String fname, String lname){
        this.email = Email;
        this.password = pwd;
        this.first_name = fname;
        this.last_name = lname;
    }

    public SignupRequest(String Email, String pwd, String fname, String lname,String phone_number){
        this.email = Email;
        this.password = pwd;
        this.first_name = fname;
        this.last_name = lname;
        this.phone_number=phone_number;
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

    public String getPhone_no(){
        return phone_number;
    }
    public void setPhone_no(String phone_no){
        this.phone_number=phone_number;
    }

}
