package com.tripalocal.bentuke.helpers;

/**
 * Created by naveen on 4/6/2015.
 */
public class SearchRequest {
    private String start_datetime;
    private String end_datetime;
    private String city;
    private String guest_number;
    private String keywords;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchRequest(String start_date, String end_date, String city, String guest_number, String keywords) {
        this.start_datetime = start_date;
        this.end_datetime = end_date;
        this.city = city;
        this.guest_number = guest_number;
        this.keywords = keywords;
    }

    public SearchRequest(String start_date, String end_date, String city, String guest_number, String keywords,String type) {
        this.start_datetime = start_date;
        this.end_datetime = end_date;
        this.city = city;
        this.guest_number = guest_number;
        this.keywords = keywords;
        this.type=type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGuest_number() {
        return guest_number;
    }

    public void setGuest_number(String guest_number) {
        this.guest_number = guest_number;
    }
}
