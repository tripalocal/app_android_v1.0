package com.tripalocal.bentuke.models.network;

import java.util.ArrayList;
import java.util.List;

import com.tripalocal.bentuke.models.Experience;

/**
 * Created by naveen on 4/19/2015.
 */
public class Search_Result {

        private List<Experience> experiences;
        private String date;
        private String city;

    public Search_Result (ArrayList<Experience> exp, String City, String Date,String type){
            experiences = new ArrayList<>();
            if(exp.size() > 0){
                this.experiences = exp;
            }
            this.city = City;
            this.date = Date;

        }

        public List<Experience> getExperiences() {
            return experiences;
        }

    public List<Experience> getHostExperiences(){
        ArrayList<Experience> explist=new ArrayList<Experience>();
        for(Experience exp: experiences){
            if(exp.getType().equals("Multi-hosts")){
                explist.add(exp);
            }
        }
        return explist;
    }

    public List<Experience> getPrivateExperiences(){
        ArrayList<Experience> explist=new ArrayList<Experience>();
        for(Experience exp: experiences){
            if(exp.getType().equals("Private")){
                explist.add(exp);
            }
        }
        return explist;
    }
        public void setExperiences(ArrayList<Experience> experiences) {
            this.experiences = experiences;
        }


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
