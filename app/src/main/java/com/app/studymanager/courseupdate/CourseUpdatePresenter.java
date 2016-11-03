package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 03-11-2016.
 */

public interface CourseUpdatePresenter {

    void onResume(Credentials credentials, long courseId);

    void onDestroy();
}
