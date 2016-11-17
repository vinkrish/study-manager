package com.app.studymanager.models;

import java.io.Serializable;

/**
 * Created by Vinay on 31-10-2016.
 */

public class CourseSettings implements Serializable {
    private WeeklyHours weeklyHours;
    private String proficiency;
    private String targetDate;
    private String defaultView;
    private ProficiencyValue proficiencyValue;

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

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }

    public ProficiencyValue getProficiencyValue() {
        return proficiencyValue;
    }

    public void setProficiencyValue(ProficiencyValue proficiencyValue) {
        this.proficiencyValue = proficiencyValue;
    }
}
