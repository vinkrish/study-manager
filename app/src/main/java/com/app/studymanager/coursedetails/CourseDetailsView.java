package com.app.studymanager.coursedetails;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public interface CourseDetailsView {
    void showProgress();

    void hideProgess();

    void setCourse(Course courses);

    void showSubscribed();

    void showUnsubscribed();

    void showError();
}
