package com.app.studymanager.singup;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface SignupView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setValidEmailError();

    void setPasswordError();

    void setValidPasswordError();

    void setEmailExist();

    void showSignupSuccesful();

    void showSignupFailed();

    void navigateToLogin();
}
