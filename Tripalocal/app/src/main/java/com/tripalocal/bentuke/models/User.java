package com.tripalocal.bentuke.models;

import java.util.List;

/**
 * Created by naveen on 4/29/2015.
 */
public class User {

    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String localContactNo;
    private String roamingContactNo;
    private List<String> favourites;
    private String login_token;
    private boolean profileCreated;
    private boolean loggedin;

    public boolean isProfileCreated() {
        return profileCreated;
    }

    public void setProfileCreated(boolean profileCreated) {
        this.profileCreated = profileCreated;
    }

    private List<Experience> upcomingExperiences;
    private List<Experience> pastExperiences;

    public User(){
    }

    public User(String fname, String lname,String DOB, String Email) {
        firstName = fname;
        lastName = lname;
        dob = DOB;
        email = Email;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLocalContactNo() {
        return localContactNo;
    }

    public void setLocalContactNo(String localContactNo) {
        this.localContactNo = localContactNo;
    }

    public String getRoamingContactNo() {
        return roamingContactNo;
    }

    public void setRoamingContactNo(String roamingContactNo) {
        this.roamingContactNo = roamingContactNo;
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }

    public List<Experience> getUpcomingExperiences() {
        return upcomingExperiences;
    }

    public void setUpcomingExperiences(List<Experience> upcomingExperiences) {
        this.upcomingExperiences = upcomingExperiences;
    }

    public List<Experience> getPastExperiences() {
        return pastExperiences;
    }

    public void setPastExperiences(List<Experience> pastExperiences) {
        this.pastExperiences = pastExperiences;
    }
}
