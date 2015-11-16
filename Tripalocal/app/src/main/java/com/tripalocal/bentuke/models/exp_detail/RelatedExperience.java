package com.tripalocal.bentuke.models.exp_detail;

/**
 * Created by Fang on 14/10/2015.
 */
public class RelatedExperience {
    public String image,title,dollarsign,language;
    private int id;
    private double price,duration;

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelatedExperience(int id,String image, String title, String dollarsign, String language, double duration, double price) {
        this.image = image;
        this.title = title;
        this.dollarsign = dollarsign;
        this.language = language;

        this.duration = duration;
        this.price = price;
        this.id=id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDollarsign() {
        return dollarsign;
    }

    public void setDollarsign(String dollarsign) {
        this.dollarsign = dollarsign;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
