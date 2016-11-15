package com.app.studymanager.profile;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.Profile;
import com.app.studymanager.subscribedcourses.SubscribedCoursesInteractor;

import java.util.List;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface ProfileInteractor {
    interface OnFinishedListener {
        void onFinished(Profile profile);

        void onFinished(List<Course> courses);

        void onSuccess();

        void onError();
    }

    void getProfile(Credentials credentials, OnFinishedListener listener);

    void updateProfile(Credentials credentials, String name, OnFinishedListener listener);

    void fetchSubscribedCourses(Credentials credentials, OnFinishedListener listener);
}
