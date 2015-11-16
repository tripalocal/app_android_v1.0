package com.tripalocal.bentuke.models.network;

/**
 * Created by naveen on 6/21/2015.
 */
public class profileUpdateRequest {
    private String phone_number;

    public profileUpdateRequest(String no){
        this.phone_number =no;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
