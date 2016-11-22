package com.app.studymanager.coursesettings;

import com.app.studymanager.models.CourseSettings;

/**
 * Created by Vinay on 31-10-2016.
 */

public interface CourseSettingsView {
    void showProgress();

    void hideProgess();

    void setCourseSettings(CourseSettings courseSettings);

    void showSaved();

    void showInputError();

    void showError();

    void showAPIError(String message);
}
