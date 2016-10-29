package com.app.studymanager.courses;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public class CoursesPresenterImpl implements CoursesPresenter, CoursesInteractor.OnFinishedListener {
    private CoursesView coursesView;
    private CoursesInteractor coursesInteractor;

    public CoursesPresenterImpl(CoursesView coursesView, CoursesInteractor coursesInteractor) {
        this.coursesView = coursesView;
        this.coursesInteractor = coursesInteractor;
    }

    @Override
    public void onResume(int userId, String authToken) {
        if(coursesView != null) {
            coursesView.showProgress();
            coursesInteractor.fetchCourses(userId, authToken, this);
        }
    }

    @Override
    public void onDestroy() {
        coursesView = null;
    }

    @Override
    public void onFinished(List<Course> courses) {
        if(coursesView != null) {
            coursesView.setCourses(courses);
            coursesView.hideProgess();
        }
    }
}
