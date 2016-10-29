package com.app.studymanager.singup;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface SignupInteractor {

    interface OnSignupFinishedListener{
        void onEmailError();

        void onValidEmailError();

        void onPasswordError();

        void onValidPasswordError();

        void onEmailExist();

        void onSuccess();

        void onFailure();
    }

    void signup(String email, String password, OnSignupFinishedListener listener);
}
