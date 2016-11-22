package com.app.studymanager.custombook;

/**
 * Created by Vinay on 07-11-2016.
 */

public interface CustomBookView {
    void showProgress();

    void hideProgess();

    void setAdd();

    void showError();

    void showAPIError(String message);
}
