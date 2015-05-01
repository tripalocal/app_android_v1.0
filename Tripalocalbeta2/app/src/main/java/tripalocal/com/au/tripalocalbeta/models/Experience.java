package tripalocal.com.au.tripalocalbeta.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveen on 4/19/2015.
 */
public class Experience {

    private String title;
    private List<Timeslot> timeslots = new ArrayList<Timeslot>();
    private String meetupSpot;
    private Double rate;
    private Double price;
    private Boolean instantBooking;
    private String host;
    private String hostImage;
    private Integer duration;
    private Integer id;

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
        return meetupSpot;
    }

    public void setMeetupSpot(String meetupSpot) {
        this.meetupSpot = meetupSpot;
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
        return instantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instantBooking = instantBooking;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostImage() {
        return hostImage;
    }

    public void setHostImage(String hostImage) {
        this.hostImage = hostImage;
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



}
