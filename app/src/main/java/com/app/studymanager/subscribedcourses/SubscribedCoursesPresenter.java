package com.app.studymanager.subscribedcourses;

/*
 * Created by Vinay on 27-10-2016.
 */

public interface SubscribedCoursesPresenter {
    void onResume(int userId, String authToken);

    void onCourseSelected(long courseId);

    void onDestroy();
}
