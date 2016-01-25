package com.tripalocal.bentuke.models.exp_detail;

import java.util.List;

/**
 * Created by YiHan on 25/01/2016.
 */
public abstract class AbstractExperience {

    public abstract String getHost_firstname();
    public abstract String getHost_image();
    public abstract String getTitle();
    public abstract String getLanguage();
    public abstract Double getDuration();
    public abstract Double getPrice();
    public abstract Integer getExperience_guest_number_min();
    public abstract Integer getExperience_guest_number_max();
    public abstract List<List<String>> getAvailable_date();
    public abstract List<AvailableOption> getAvailable_options();
    public abstract Float[] getExperience_dynamic_price();
}
