package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.SubscribedCourses;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface SubscribedCoursesInteractor {

    interface OnFinishedListener {

        void onFinished(SubscribedCourses courses);

    }

    void fetchSubscribedCourses(int userId, String authToken, OnFinishedListener listener);
}
