package tripalocal.com.au.tripalocalbeta.models;

/**
 * Created by naveen on 4/30/2015.
 */
public class Timeslot {
    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Boolean isInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instantBooking = instantBooking;
    }

    public Integer getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    private String timeString;
        private Boolean instantBooking;
        private Integer availableSeat;
}
