package com.app.studymanager.util;

/**
 * Created by Vinay on 04-12-2016.
 */

public class CourseStatus {

    public static String getValue(String status) {
        switch (status) {
            case "ON_TRACK":
                return "On Track";
            case "BEHIND_SCHEDULE":
                return "Behind Schedule";
            case "NOT_STARTED":
                return  "Not Started";
            default:
                return "";
        }
    }
}
