package com.app.studymanager.login;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener{

        void onLoginFailed();

        void onSuccess(Credentials credentials);
    }

    void login(String email, String password, OnLoginFinishedListener listener);
}
