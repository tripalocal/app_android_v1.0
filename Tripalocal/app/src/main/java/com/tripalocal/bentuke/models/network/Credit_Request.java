package com.tripalocal.bentuke.models.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravina on 20/06/2015.
 */
public class Credit_Request {

    private List<ItineraryString> itinerary_string = new ArrayList<ItineraryString>();
    private String card_number;
    private Integer expiration_month;
    private Integer expiration_year;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    private String coupon;

    public Credit_Request(String no,int month,int year, int Cvv,String Coupon, List<ItineraryString> itineraries){
        this.card_number = no;
        this.expiration_month = month;
        this.expiration_year = year;
        this.cvv = Cvv;
        this.coupon =Coupon;
        this.itinerary_string = itineraries;
    }

    public List<ItineraryString> getItinerary_string() {
        return itinerary_string;
    }

    public void setItinerary_string(List<ItineraryString> itinerary_string) {
        this.itinerary_string = itinerary_string;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Integer getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(Integer expiration_month) {
        this.expiration_month = expiration_month;
    }

    public Integer getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(Integer expiration_year) {
        this.expiration_year = expiration_year;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    private Integer cvv;

    public static class ItineraryString{
        private String id;
        private String date;
        private String time;

        public ItineraryString(String ID, String Date, String Time, int guest_num) {
            this.id = ID;
            this.date = Date;
            this.time = Time;
            this.guest_number = guest_num;
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

        public Integer getGuest_number() {
            return guest_number;
        }

        public void setGuest_number(Integer guest_number) {
            this.guest_number = guest_number;
        }

        private Integer guest_number;
    }
}
