package com.tripalocal.bentuke.models.network;

/**
 * Created by Frank on 31/07/2015.
 */
public class Wishlist_Update_Result {
    public Wishlist_Update_Result(String added) {
        this.added = added;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String added;

}
