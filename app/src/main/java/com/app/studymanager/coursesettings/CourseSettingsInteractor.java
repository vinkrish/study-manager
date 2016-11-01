package com.app.studymanager.coursesettings;

import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 31-10-2016.
 */

public interface CourseSettingsInteractor {
    interface OnFinishedListener {
        void onFinished(CourseSettings courseSettings);

        void onSaved();

        void onError();
    }
    void fetchCourseSettings(Credentials credentials, long courseId,
                             CourseSettingsInteractor.OnFinishedListener listener);

    void saveCourseSettings(Credentials credentials, CourseSettings courseSettings, long courseId,
                            CourseSettingsInteractor.OnFinishedListener listener);
}
