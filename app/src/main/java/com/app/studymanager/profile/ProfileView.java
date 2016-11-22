package com.app.studymanager.profile;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Profile;
import com.app.studymanager.models.SubscribedCourses;

import java.util.List;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface ProfileView {
    void showProgress();

    void hideProgess();

    void setError();

    void showAPIError(String message);

    void setSuccess();

    void setProfile(Profile profile);

    void setSubscribedCourses(SubscribedCourses subscribedCourses);
}
