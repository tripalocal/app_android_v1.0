package com.tripalocal.bentuke.models.network;

/**
 * Created by Frank on 31/07/2015.
 */
public class WishList_update_Request {

    public int user_id,experience_id;
    public String added;
    public static String added_true="True",added_false="False";

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getExperience_id() {
        return experience_id;
    }

    public void setExperience_id(int experience_id) {
        this.experience_id = experience_id;
    }

    public void WishList_update_Request(int user_id,int experience_id,String added){
        this.user_id=user_id;
        this.experience_id=experience_id;
        this.added=added;
    }


}
