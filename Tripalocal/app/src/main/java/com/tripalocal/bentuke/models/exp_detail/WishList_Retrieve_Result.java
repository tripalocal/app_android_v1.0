package com.tripalocal.bentuke.models.exp_detail;

/**
 * Created by Frank on 31/07/2015.
 */
public class WishList_Retrieve_Result {
    public WishList_Retrieve_Result(String[] ids) {
        this.ids = ids;
    }

    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
