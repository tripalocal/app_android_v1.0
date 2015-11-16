package com.tripalocal.bentuke.models.network;

/**
 * Created by naveen on 6/19/2015.
 */
public class Coupon_Request {
    private String coupon;
    private String id;
    private String date;

    public Coupon_Request(String coupon, String id, String date, String time, int guest_number) {
        this.coupon = coupon;
        this.id = id;
        this.date = date;
        this.time = time;
        this.guest_number = guest_number;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGuest_number() {
        return guest_number;
    }

    public void setGuest_number(int guest_number) {
        this.guest_number = guest_number;
    }

    private String time;
    private int guest_number;
}


//{"coupon":"aasfsaf","id":"20","date":"2015/06/17","time":"4:00 - 6:00","guest_number":2}