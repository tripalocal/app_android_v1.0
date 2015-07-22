package com.tripalocal.bentuke.models.network;

/**
 * Created by chenf_000 on 21/07/2015.
 */
public class Profile_result {

    private String first_name,last_name,image;

    public Profile_result(String first_name, String last_name, String image) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.image = image;
    }
    public Profile_result(){}

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
