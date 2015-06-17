package tripalocal.com.au.tripalocalbeta.models.exp_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveen on 5/5/2015.
 */
public class Experience_Detail{

    private Boolean included_ticket;
    private Integer experience_duration;
    private Double experience_price;
    private String included_ticket_detail;
    private String experience_dynamic_price;
    private String host_bio;
    private List <List<String>> available_date = new ArrayList<List<String>>();
    private String included_food_detail;
    private String experience_title;
    private String included_transport_detail;
    private String experience_meetup_spot;
    private String host_image;
    private String experience_description;
    private List <ExperienceReview> experience_reviews = new ArrayList<ExperienceReview>();
    private Boolean included_transport;
    private Integer experience_rate;
    private String experience_interaction;
    private List<AvailableOption> available_options = new ArrayList<AvailableOption>();
    private String experience_dress;
    private String experience_activity;
    private String host_firstname;
    private String host_lastname;
    private Boolean included_food;
    private String experience_language;
    private Integer experience_guest_number_min;
    private Integer experience_guest_number_max;


    public Boolean isIncludedTicket() {
        return included_ticket;
    }

    public void setIncludedTicket(Boolean includedTicket) {
        this.included_ticket = includedTicket;
    }

    public Integer getExperience_duration() {
        return experience_duration;
    }

    public void setExperience_duration(Integer experience_duration) {
        this.experience_duration = experience_duration;
    }

    public Double getExperience_price() {
        return experience_price;
    }

    public void setExperience_price(Double experience_price) {
        this.experience_price = experience_price;
    }

    public String getIncluded_ticket_detail() {
        return included_ticket_detail;
    }

    public void setIncluded_ticket_detail(String included_ticket_detail) {
        this.included_ticket_detail = included_ticket_detail;
    }

    public String getExperience_dynamic_price() {
        return experience_dynamic_price;
    }

    public void setExperience_dynamic_price(String experience_dynamic_price) {
        this.experience_dynamic_price = experience_dynamic_price;
    }

    public String getHost_bio() {
        return host_bio;
    }

    public void setHost_bio(String host_bio) {
        this.host_bio = host_bio;
    }

    public List<List<String>> getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(List<List<String>> available_date) {
        this.available_date = available_date;
    }

    public String getIncluded_food_detail() {
        return included_food_detail;
    }

    public void setIncluded_food_detail(String included_food_detail) {
        this.included_food_detail = included_food_detail;
    }

    public String getExperience_title() {
        return experience_title;
    }

    public void setExperience_title(String experience_title) {
        this.experience_title = experience_title;
    }

    public String getIncluded_transport_detail() {
        return included_transport_detail;
    }

    public void setIncluded_transport_detail(String included_transport_detail) {
        this.included_transport_detail = included_transport_detail;
    }

    public String getExperience_meetup_spot() {
        return experience_meetup_spot;
    }

    public void setExperience_meetup_spot(String experience_meetup_spot) {
        this.experience_meetup_spot = experience_meetup_spot;
    }

    public String getHost_image() {
        return host_image;
    }

    public void setHost_image(String host_image) {
        this.host_image = host_image;
    }

    public String getExperience_description() {
        return experience_description;
    }

    public void setExperience_description(String experience_description) {
        this.experience_description = experience_description;
    }

    public List<ExperienceReview> getExperience_reviews() {
        return experience_reviews;
    }

    public void setExperience_reviews(List<ExperienceReview> experience_reviews) {
        this.experience_reviews = experience_reviews;
    }

    public Boolean isIncludedTransport() {
        return included_transport;
    }

    public void setIncluded_transport(Boolean included_transport) {
        this.included_transport = included_transport;
    }

    public Integer getExperience_rate() {
        return experience_rate;
    }

    public void setExperience_rate(Integer experience_rate) {
        this.experience_rate = experience_rate;
    }

    public String getExperience_interaction() {
        return experience_interaction;
    }

    public void setExperience_interaction(String experience_interaction) {
        this.experience_interaction = experience_interaction;
    }

    public List<AvailableOption> getAvailable_options() {
        return available_options;
    }

    public void setAvailable_options(List<AvailableOption> available_options) {
        this.available_options = available_options;
    }

    public String getExperience_dress() {
        return experience_dress;
    }

    public void setExperience_dress(String experience_dress) {
        this.experience_dress = experience_dress;
    }

    public String getExperience_activity() {
        return experience_activity;
    }

    public void setExperience_activity(String experience_activity) {
        this.experience_activity = experience_activity;
    }

    public String getHost_firstname() {
        return host_firstname;
    }

    public void setHost_firstname(String host_firstname) {
        this.host_firstname = host_firstname;
    }

    public String getHost_lastname() {
        return host_lastname;
    }

    public void setHost_lastname(String host_lastname) {
        this.host_lastname = host_lastname;
    }

    public Boolean isIncludedFood() {
        return included_food;
    }

    public void setIncluded_food(Boolean included_food) {
        this.included_food = included_food;
    }

    public String getLanguage() {return experience_language;}

    public void setLanguage(String language) {this.experience_language = language;}


    public Integer getExperience_guest_number_max() {
        return experience_guest_number_max;
    }

    public void setExperience_guest_number_max(Integer experience_guest_number_max) {
        this.experience_guest_number_max = experience_guest_number_max;
    }

    public Integer getExperience_guest_number_min() {
        return experience_guest_number_min;
    }

    public void setExperience_guest_number_min(Integer experience_guest_number_min) {
        this.experience_guest_number_min = experience_guest_number_min;
    }
}
