package com.app.studymanager.profile;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface ProfilePresenter {
    void onResume(Credentials credentials);

    void onUpdate(Credentials credentials, String name);

    void onDestroy();
}
