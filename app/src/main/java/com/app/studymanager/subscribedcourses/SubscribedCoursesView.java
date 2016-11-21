package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.SubscribedCourses;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface SubscribedCoursesView {
    void showProgress();

    void hideProgess();

    void setSubscribedCourses(SubscribedCourses subscribedCourses);

}
