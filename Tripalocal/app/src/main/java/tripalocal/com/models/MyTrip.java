package tripalocal.com.models;

import java.util.Date;
/**
 * Created by adventure on 25/04/2015.
 */
public class MyTrip {
    private String datetime;
    private String status;
    private int guest_number;
    private int experience_id;
    private String experience_title;
    private String meetup_spot;
    private String host_name;
    private String host_phone_number;
    private String host_image;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExperienceId() {
        return experience_id;
    }

    public void setExperienceId(int experience_id) {
        this.experience_id = experience_id;
    }

    public int getGuestNumber() {
        return guest_number;
    }

    public void setGuestNumber(int guest_number) {
        this.guest_number = guest_number;
    }

    public String getExperienceTitle() {
        return experience_title;
    }

    public void setExperienceTitle(String experience_title) {
        this.experience_title = experience_title;
    }

    public String getMeetupSpot() {
        return meetup_spot;
    }

    public void setMeetupSpot(String meetup_spot) {
        this.meetup_spot = meetup_spot;
    }

    public String getHostName() {
        return host_name;
    }

    public void setHostName(String host_name) {
        this.host_name = host_name;
    }

    public String getHostPhoneNumber() {
        return host_phone_number;
    }

    public void setHostPhoneNumber(String host_phone_number) {
        this.host_phone_number = host_phone_number;
    }

    public String getHostImage() {
        return host_image;
    }

    public void setHostImage(String host_image) {
        this.host_image = host_image;
    }
}
