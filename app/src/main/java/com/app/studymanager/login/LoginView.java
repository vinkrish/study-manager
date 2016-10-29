package com.app.studymanager.login;

import com.app.studymanager.models.LoginResponse;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setValidEmailError();

    void setPasswordError();

    void setLoginFailed();

    void navigateToSignup();

    void saveUserToken(LoginResponse loginResponse);

    void navigateToHome();
}
