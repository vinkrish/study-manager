package com.app.studymanager.profile;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Profile;

import java.util.List;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface ProfileView {
    void showProgress();

    void hideProgess();

    void setError();

    void setSuccess();

    void setProfile(Profile profile);

    void setSubscribedCourses(List<Course> subscribedCourses);
}
