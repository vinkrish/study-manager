package com.app.studymanager.login;

import com.app.studymanager.models.LoginResponse;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener{
        void onEmailError();

        void onValidEmailError();

        void onPasswordError();

        void onLoginFailed();

        void onSuccess(LoginResponse loginResponse);
    }

    void login(String email, String password, OnLoginFinishedListener listener);
}
