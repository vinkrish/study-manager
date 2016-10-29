package com.app.studymanager.coursedetails;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public class CourseDetailsPresenterImpl implements
        CourseDetailsPresenter, CourseDetailsInteractor.OnFinishedListener {

    private CourseDetailsView courseDetailsView;
    private CourseDetailsInteractor interactor;

    public CourseDetailsPresenterImpl(CourseDetailsView courseDetailsView, CourseDetailsInteractor interactor){
        this.courseDetailsView = courseDetailsView;
        this.interactor = interactor;
    }

    @Override
    public void onResume(int userId, String authToken, long courseId) {
        if(courseDetailsView != null) {
            courseDetailsView.showProgress();
            interactor.fetchCourseDetails(userId, authToken, courseId, this);
        }
    }

    @Override
    public void subscribeCourse(int userId, String authToken, long courseId) {
        if(courseDetailsView != null) {
            courseDetailsView.showProgress();
            interactor.subscribeToCourse(userId, authToken, courseId, this);
        }
    }

    @Override
    public void unsubscribeCourse(int userId, String authToken, long courseId) {
        if(courseDetailsView != null) {
            courseDetailsView.showProgress();
            interactor.unSubscribeToCourse(userId, authToken, courseId, this);
        }
    }

    @Override
    public void onDestroy() {
        courseDetailsView = null;
    }

    @Override
    public void onFinished(Course course) {
        if(courseDetailsView != null) {
            courseDetailsView.setCourse(course);
            courseDetailsView.hideProgess();
        }
    }

    @Override
    public void onSubscribed() {
        if(courseDetailsView != null) {
            courseDetailsView.hideProgess();
            courseDetailsView.showSubscribed();
        }
    }

    @Override
    public void onUnSubscribed() {
        if(courseDetailsView != null) {
            courseDetailsView.hideProgess();
            courseDetailsView.showUnsubscribed();
        }
    }

    @Override
    public void onError() {
        if(courseDetailsView != null) {
            courseDetailsView.hideProgess();
            courseDetailsView.showError();
        }
    }
}
