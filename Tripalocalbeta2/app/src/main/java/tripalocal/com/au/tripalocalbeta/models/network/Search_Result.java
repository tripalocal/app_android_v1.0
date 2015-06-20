package tripalocal.com.au.tripalocalbeta.models.network;

import java.util.ArrayList;
import java.util.List;

import tripalocal.com.au.tripalocalbeta.models.Experience;

/**
 * Created by naveen on 4/19/2015.
 */
public class Search_Result {

        private List<Experience> experiences;
        private String date;
        private String city;

        public Search_Result (ArrayList<Experience> exp, String City, String Date){
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
