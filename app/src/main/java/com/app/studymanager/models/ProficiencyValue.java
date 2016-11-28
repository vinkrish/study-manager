package com.app.studymanager.models;

import java.io.Serializable;

/**
 * Created by Vinay on 17-11-2016.
 */

public class ProficiencyValue implements Serializable {
    private int beginner;
    private int normal;
    private int expert;

    public int getBeginner() {
        return beginner;
    }

    public void setBeginner(int beginner) {
        this.beginner = beginner;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getExpert() {
        return expert;
    }

    public void setExpert(int expert) {
        this.expert = expert;
    }
}
