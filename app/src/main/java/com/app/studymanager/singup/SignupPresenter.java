package com.app.studymanager.singup;

/**
 * Created by Vinay on 22-10-2016.
 */

public interface SignupPresenter {
    void validateCredentials(String email, String password);

    void onDestroy();
}
