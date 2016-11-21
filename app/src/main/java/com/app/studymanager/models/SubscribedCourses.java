package com.app.studymanager.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vinay on 21-11-2016.
 */

public class SubscribedCourses {
    private List<Course> courses;
    private String lastUpdatedDate;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
