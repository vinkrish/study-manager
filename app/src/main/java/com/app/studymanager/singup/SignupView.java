package com.app.studymanager.singup;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface SignupView {
    void showProgress();

    void hideProgress();

    void setEmailExist();

    void showSignupSuccessful();

    void showSignupFailed();

    void navigateToLogin();
}
