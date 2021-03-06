package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.SubscribedCourses;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface SubscribedCoursesInteractor {

    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onFinished(SubscribedCourses courses);

        void onSaved();
    }

    void fetchSubscribedCourses(int userId, String authToken, OnFinishedListener listener);

    void saveBookRead(Credentials credentials, long courseId, Book book, OnFinishedListener listener);
}
