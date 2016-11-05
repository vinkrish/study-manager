package com.app.studymanager.login;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setLoginFailed();

    void navigateToSignup();

    void saveUserToken(Credentials credentials);

    void navigateToHome();
}
