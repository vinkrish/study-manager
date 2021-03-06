package com.app.studymanager.customcourse;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 10-11-2016.
 */

public interface CustomCourseInteractor {
    interface OnFinishedListener {
        void onAdded();

        void onError();

        void onAPIError(String message);
    }

    void addCustomCourse(Credentials credentials, Course course,
                       OnFinishedListener listener);
}
