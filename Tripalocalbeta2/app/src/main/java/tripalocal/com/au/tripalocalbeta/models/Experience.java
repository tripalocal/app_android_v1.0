package tripalocal.com.au.tripalocalbeta.models;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
