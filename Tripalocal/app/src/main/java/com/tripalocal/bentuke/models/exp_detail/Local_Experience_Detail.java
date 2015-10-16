package com.tripalocal.bentuke.models.exp_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 8/10/2015.
 */
public class Local_Experience_Detail {

    private Integer experience_duration;
    private Double experience_price;
    //private String experience_dynamic_price;
    private Float[] experience_dynamic_price;
    private List <List<String>> available_date = new ArrayList<List<String>>();
    private List <ExperienceReview> experience_reviews = new ArrayList<ExperienceReview>();
    private Integer experience_rate;
    private List<AvailableOption> available_options = new ArrayList<AvailableOption>();
    private String experience_language;
    private ArrayList<RelatedExperience> related_experiences;

    public ArrayList<RelatedExperience> getRelated_experiences() {
        return related_experiences;
    }

    public void setRelated_experiences(ArrayList<RelatedExperience> related_experiences) {
        this.related_experiences = related_experiences;
    }

    public String getPickup_detail() {
        return pickup_detail;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDisclamier() {
        return disclamier;
    }

    public void setDisclamier(String disclamier) {
        this.disclamier = disclamier;
    }

    public String getRefund_policy() {
        return refund_policy;
    }

    public void setRefund_policy(String refund_policy) {
        this.refund_policy = refund_policy;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Local_Experience_Detail(Integer experience_duration, Double experience_price, Float[] experience_dynamic_price,
                                   List<List<String>> available_date, List<ExperienceReview> experience_reviews,
                                   Integer experience_rate, List<AvailableOption> available_options, String experience_language,
                                   Integer experience_guest_number_min, Integer experience_guest_number_max, String title, String description,
                                   String highlights, String notice, String tips, String whatsincluded, String pickup_detail, ArrayList<String> experience_images
                                ,String service,String schedule,String disclamier,String refund_policy,String insurance,
                                   ArrayList<RelatedExperience> related_experiences) {
        this.experience_duration = experience_duration;
        this.experience_price = experience_price;
        this.experience_dynamic_price = experience_dynamic_price;
        this.available_date = available_date;
        this.experience_reviews = experience_reviews;
        this.experience_rate = experience_rate;
        this.available_options = available_options;
        this.experience_language = experience_language;
        this.experience_guest_number_min = experience_guest_number_min;
        this.experience_guest_number_max = experience_guest_number_max;
        this.title = title;
        this.description = description;
        this.highlights = highlights;
        this.notice = notice;
        this.tips = tips;
        this.whatsincluded = whatsincluded;
        this.pickup_detail = pickup_detail;
        this.experience_images = experience_images;
        this.service=service;
        this.schedule=schedule;
        this.disclamier=disclamier;
        this.refund_policy=refund_policy;
        this.insurance=insurance;
        this.related_experiences=related_experiences;
    }

    public void setPickup_detail(String pickup_detail) {
        this.pickup_detail = pickup_detail;
    }

    public String getExperience_language() {
        return experience_language;
    }

    public void setExperience_language(String experience_language) {
        this.experience_language = experience_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getWhatsincluded() {
        return whatsincluded;
    }

    public void setWhatsincluded(String whatsincluded) {
        this.whatsincluded = whatsincluded;
    }

    private Integer experience_guest_number_min;
    private Integer experience_guest_number_max;
    private String title,description,highlights,notice,tips,whatsincluded,
            pickup_detail,service,schedule,disclamier,
    refund_policy,insurance;

    public ArrayList<String> getExperience_images() {
        return experience_images;
    }

    public void setExperience_images(ArrayList<String> experience_images) {
        this.experience_images = experience_images;
    }

    private ArrayList<String> experience_images;



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


    public Float[] getExperience_dynamic_price() {
        return experience_dynamic_price;
    }

    public void setExperience_dynamic_price(Float[] experience_dynamic_price) {
        this.experience_dynamic_price = experience_dynamic_price;
    }


    public List<List<String>> getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(List<List<String>> available_date) {
        this.available_date = available_date;
    }



    public List<ExperienceReview> getExperience_reviews() {
        return experience_reviews;
    }

    public void setExperience_reviews(List<ExperienceReview> experience_reviews) {
        this.experience_reviews = experience_reviews;
    }



    public Integer getExperience_rate() {
        return experience_rate;
    }

    public void setExperience_rate(Integer experience_rate) {
        this.experience_rate = experience_rate;
    }


    public List<AvailableOption> getAvailable_options() {
        return available_options;
    }

    public void setAvailable_options(List<AvailableOption> available_options) {
        this.available_options = available_options;
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
