package tripalocal.com.models.exp_detail;

/**
 * Created by naveen on 5/5/2015.
 */
public class AvailableOption {
    private String date_string;
    private Boolean instant_booking;
    private String time_string;
    private Integer available_seat;
    private String datetime;

    public String getDate_string() {
        return date_string;
    }

    public void setDate_string(String date_string) {
        this.date_string = date_string;
    }

    public Boolean isInstantBooking() {
        return instant_booking;
    }

    public void setInstant_booking(Boolean instant_booking) {
        this.instant_booking = instant_booking;
    }

    public String getTime_string() {
        return time_string;
    }

    public void setTime_string(String time_string) {
        this.time_string = time_string;
    }

    public Integer getAvailable_seat() {
        return available_seat;
    }

    public void setAvailable_seat(Integer available_seat) {
        this.available_seat = available_seat;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
