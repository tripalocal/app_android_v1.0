package com.tripalocal.bentuke.models.exp_detail;

/**
 * Created by naveen on 5/5/2015.
 */
public class ExperienceReview {

    private String reviewer_image;
    private String review_comment;
    private String reviewer_firstname;
    private String reviewer_lastname;

    public String getReviewer_image() {
        return reviewer_image;
    }

    public void setReviewer_image(String reviewer_image) {
        this.reviewer_image = reviewer_image;
    }

    public String getReview_comment() {
        return review_comment;
    }

    public void setReview_comment(String review_comment) {
        this.review_comment = review_comment;
    }

    public String getReviewer_firstname() {
        return reviewer_firstname;
    }

    public void setReviewer_firstname(String reviewer_firstname) {
        this.reviewer_firstname = reviewer_firstname;
    }

    public String getReviewer_lastname() {
        return reviewer_lastname;
    }

    public void setReviewer_lastname(String reviewer_lastname) {
        this.reviewer_lastname = reviewer_lastname;
    }
}
