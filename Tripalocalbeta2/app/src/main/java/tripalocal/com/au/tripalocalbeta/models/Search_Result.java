package tripalocal.com.au.tripalocalbeta.models;

import java.util.Date;

/**
 * Created by naveen on 4/19/2015.
 */
public class Search_Result {

    private Experience[] experiences;
    private String date;

    public Experience[] getExperience() {
        return experiences;
    }

    public void setExperience(Experience[] experience) {
        this.experiences = experience;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
