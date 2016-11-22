package com.app.studymanager.courses;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface CoursesInteractor {
    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onFinished(List<Course> courses);
    }

    void fetchCourses(int userId, String authToken, CoursesInteractor.OnFinishedListener listener);
}
