package com.app.studymanager.courses;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface CoursesPresenter {

    void onResume(int userId, String authToken);

    void onDestroy();
}
