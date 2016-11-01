package com.app.studymanager.coursesettings;

import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 31-10-2016.
 */

public interface CourseSettingsPresenter {
    void onResume(Credentials credentials, long courseId);

    void onSave(Credentials credentials, CourseSettings settings, long courseId);

    void onDestroy();
}
