package com.app.studymanager.customcourse;

/**
 * Created by Vinay on 10-11-2016.
 */

public interface CustomCourseView {
    void showProgress();

    void hideProgess();

    void setAdd();

    void showError();

    void showAPIError(String message);
}
