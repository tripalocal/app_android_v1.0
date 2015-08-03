package com.tripalocal.bentuke.models.exp_detail;

/**
 * Created by Frank on 31/07/2015.
 */
public class WishList_Retrieve_Result {
    public WishList_Retrieve_Result(int[] ids) {
        this.ids = ids;
    }

    private int[] ids;

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }
}
