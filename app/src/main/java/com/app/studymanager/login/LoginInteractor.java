package com.app.studymanager.login;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener{

        void onSuccess(Credentials credentials);

        void onPwdRecovered();

        void onNoUser();

        void onError();

        void onAPIError(String message);
    }

    void login(String email, String password, OnLoginFinishedListener listener);

    void recoverPwd(String email, OnLoginFinishedListener listener);
}
