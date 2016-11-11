package com.app.studymanager.pwdrecovery;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface PwdPresenter {
    void pwdReset(Credentials credentials, String email, String password);

    void onDestroy();
}
