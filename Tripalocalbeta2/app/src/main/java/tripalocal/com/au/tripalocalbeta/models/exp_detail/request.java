package tripalocal.com.au.tripalocalbeta.models.exp_detail;

/**
 * Created by naveen on 5/5/2015.
 */
public class request {
    public int getExperience_id() {
        return experience_id;
    }

    public void setExperience_id(int experience_id) {
        this.experience_id = experience_id;
    }

    private int experience_id;

    public request( int value) {
        this.experience_id = value;
    }
}
