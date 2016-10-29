package com.app.studymanager.login;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface LoginPresenter {
    void validateCredentials(String email, String password);

    void onDestroy();
}
