package com.app.studymanager.coursedetails;

/**
 * Created by Vinay on 28-10-2016.
 */

public interface CourseDetailsPresenter {

    void onResume(int userId, String authToken, long courseId);

    void subscribeCourse(int userId, String authToken, long courseId);

    void unsubscribeCourse(int userId, String authToken, long courseId);

    void onDestroy();
}
