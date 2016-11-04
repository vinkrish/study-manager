package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 03-11-2016.
 */

public interface CourseUpdateInteractor {
    interface OnFinishedListener {
        void onFinished(Course course);

        void onUpdated();

        void onUnSubscribed();

        void onError();
    }

    void fetchCourseDetails(Credentials credentials, long courseId,
                            CourseUpdateInteractor.OnFinishedListener listener);

    void updateSubscribedCourse(Credentials credentials, long courseId,
                                Book book,
                                CourseUpdateInteractor.OnFinishedListener listener);

    void unSubscribeToCourse(Credentials credentials, long courseId,
                             CourseUpdateInteractor.OnFinishedListener listener);
}
