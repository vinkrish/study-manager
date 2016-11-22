package com.app.studymanager.pwdrecovery;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface PwdInteractor {

    interface OnFinishedListener{
        void onPwdReset();

        void onError();

        void onAPIError(String message);
    }

    void resetPwd(Credentials credentials, String email, String password,
                  OnFinishedListener listener);
}
