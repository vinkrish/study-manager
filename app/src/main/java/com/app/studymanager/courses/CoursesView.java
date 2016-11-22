package com.app.studymanager.courses;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public interface CoursesView {
    void showProgress();

    void hideProgess();

    void showError();

    void showAPIError(String message);

    void setCourses(List<Course> courses);

    void navigateToCourseDetails(Course course);
}
