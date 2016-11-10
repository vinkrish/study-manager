package com.app.studymanager.customcourse;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 10-11-2016.
 */

public interface CustomCoursePresenter {
    void onAdd(Credentials credentials, Course course);

    void onDestroy();
}
