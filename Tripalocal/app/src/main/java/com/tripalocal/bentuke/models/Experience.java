package com.tripalocal.bentuke.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveen on 4/19/2015.
 */
public class Experience {

    private String title;
    private List<Timeslot> timeslots = new ArrayList<Timeslot>();
    private String meetup_spot;
    private Double rate;
    private Double price;
    private Boolean instant_booking;
    private String host;
    private String host_image;
    private Integer duration;
    private Integer id;
    private String description;
    private String language;
    private String photo_url;//ArrayList:experience_images,mytrip:experience_photo
    private String type;

    public String getPhoto_url() {
        return photo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Experience(){

    }

    public Experience(String title, Integer id, String description) {
        this.title = title;
        this.id = id;
        this.description = description;
    }

    public Experience(String title, List<Timeslot> timeslots, String meetup_spot, Double rate, Double price, Boolean instant_booking, String host, String host_image, Integer duration, Integer id, String description, String language,String photo_url) {
        this.title = title;
        this.timeslots = timeslots;
        this.meetup_spot = meetup_spot;
        this.rate = rate;
        this.price = price;
        this.instant_booking = instant_booking;
        this.host = host;
        this.host_image = host_image;
        this.duration = duration;
        this.id = id;
        this.description = description;
        this.language = language;
        this.photo_url=photo_url;
    }

    public Experience(String title, Double price, String host_image, Integer duration, Integer id, String description, String language,String photo_url) {
        this.title = title;
        this.price = price;
        this.host_image = host_image;
        this.duration = duration;
        this.id = id;
        this.description = description;
        this.photo_url=photo_url;

        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Experience(String title, List<Timeslot> timeslots, String meetup_spot, Double rate, Double price, Boolean instant_booking, String host, String host_image, Integer duration, Integer id, String description, String language,String photo_url,String type) {
        this.title = title;
        this.timeslots = timeslots;
        this.meetup_spot = meetup_spot;
        this.rate = rate;
        this.price = price;
        this.instant_booking = instant_booking;
        this.host = host;
        this.host_image = host_image;
        this.duration = duration;
        this.id = id;
        this.description = description;
        this.language = language;
        this.photo_url=photo_url;
        this.type=type;
    }

    public Experience(String title, Double price, String host_image, Integer duration, Integer id, String description, String language,String photo_url,String type) {
        this.title = title;
        this.price = price;
        this.host_image = host_image;
        this.duration = duration;
        this.id = id;
        this.description = description;
        this.photo_url=photo_url;
        this.language = language;
        this.type=type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public String getMeetupSpot() {
        return meetup_spot;
    }

    public void setMeetupSpot(String meetupSpot) {
        this.meetup_spot = meetupSpot;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isInstantBooking() {
        return instant_booking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instant_booking = instantBooking;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostImage() {
        return host_image;
    }

    public void setHostImage(String hostImage) {
        this.host_image = hostImage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {return language;}

    public void setLanguage(String language) {this.language = language;}
}
