package com.app.studymanager.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vinay on 31-10-2016.
 */

public class CourseSettings implements Serializable {
    @SerializedName("weeklyHours")
    private WeeklyHours weeklyHours;
    private String proficiency;
    private String targetDate;

    public WeeklyHours getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(WeeklyHours weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
