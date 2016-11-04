package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 03-11-2016.
 */

public class CourseUpdatePresenterImpl implements CourseUpdatePresenter,
        CourseUpdateInteractor.OnFinishedListener {

    private CourseUpdateView updateView;
    private CourseUpdateInteractor interactor;

    public CourseUpdatePresenterImpl(CourseUpdateView updateView, CourseUpdateInteractor interactor) {
        this.updateView = updateView;
        this.interactor = interactor;
    }

    @Override
    public void onResume(Credentials credentials, long courseId) {
        if(updateView != null) {
            updateView.showProgress();
            interactor.fetchCourseDetails(credentials, courseId, this);
        }
    }

    @Override
    public void onUpdate(Credentials credentials, long courseId, Book book) {
        if(updateView != null) {
            updateView.showProgress();
            interactor.updateSubscribedCourse(credentials, courseId, book, this);
        }
    }

    @Override
    public void unsubscribeCourse(Credentials credentials, long courseId) {
        if(updateView != null) {
            updateView.showProgress();
            interactor.unSubscribeToCourse(credentials, courseId, this);
        }
    }

    @Override
    public void onDestroy() {
        updateView = null;
    }

    @Override
    public void onFinished(Course course) {
        if(updateView != null) {
            updateView.setCourse(course);
            updateView.hideProgess();
        }
    }

    @Override
    public void onUpdated() {
        if(updateView != null) {
            updateView.setUpdate();
            updateView.hideProgess();
        }
    }

    @Override
    public void onUnSubscribed() {
        if(updateView != null){
            updateView.hideProgess();
            updateView.whileUnscribed();
        }
    }

    @Override
    public void onError() {
        if(updateView != null) {
            updateView.hideProgess();
            updateView.showError();
        }
    }
}
