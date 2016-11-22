package com.app.studymanager.singup;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface SignupInteractor {

    interface OnSignupFinishedListener{

        void onEmailExist();

        void onSuccess();

        void onFailure();

        void onAPIError(String message);
    }

    void signup(String email, String password, OnSignupFinishedListener listener);
}
