package com.app.studymanager.coursedetails;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public interface CourseDetailsInteractor {
    interface OnFinishedListener {
        void onFinished(Course course);

        void onSubscribed();

        void onUnSubscribed();

        void onError();

        void onAPIError(String message);
    }

    void fetchCourseDetails(int userId, String authToken, long courseId,
                            CourseDetailsInteractor.OnFinishedListener listener);

    void subscribeToCourse(int userId, String authToken, long courseId,
                           CourseDetailsInteractor.OnFinishedListener listener);

    void unSubscribeToCourse(int userId, String authToken, long courseId,
                             CourseDetailsInteractor.OnFinishedListener listener);
}
