package com.tripalocal.bentuke.models.network;

/**
 * Created by naveen on 5/6/2015.
 */
public class MyProfile_result {
    /*id':user.id, 'first_name':user.first_name, 'last_name':user.last_name, 'email':user.email,
            'image':user.registereduser.image_url,'phone_number':profile.phone_number,'bio':profile.bio,'rate':profile.rate*/

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String image;
    private String phone_number;
    private String bio;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private String rate;
}
