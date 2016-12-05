package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.SubscribedCourses;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesPresenterImpl implements
        SubscribedCoursesPresenter,
        SubscribedCoursesInteractor.OnFinishedListener {

    private SubscribedCoursesView subscribedCoursesView;
    private SubscribedCoursesInteractor subscribedCoursesInteractor;

    public SubscribedCoursesPresenterImpl(SubscribedCoursesView subscribedCoursesView,
                                          SubscribedCoursesInteractor subscribedCoursesInteractor){
        this.subscribedCoursesView = subscribedCoursesView;
        this.subscribedCoursesInteractor = subscribedCoursesInteractor;
    }


    @Override
    public void onResume(int userId, String authToken) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.showProgress();
            subscribedCoursesInteractor.fetchSubscribedCourses(userId, authToken, this);
        }
    }

    @Override
    public void onSave(Credentials credentials, long courseId, Book book) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.showProgress();
            subscribedCoursesInteractor.saveBookRead(credentials, courseId, book, this);
        }
    }

    @Override
    public void onDestroy() {
        subscribedCoursesView = null;
    }

    @Override
    public void onError() {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.showError();
            subscribedCoursesView.hideProgess();
        }
    }

    @Override
    public void onAPIError(String message) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.hideProgess();
            subscribedCoursesView.showAPIError(message);
        }
    }

    @Override
    public void onFinished(SubscribedCourses courses) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.setSubscribedCourses(courses);
            subscribedCoursesView.hideProgess();
        }
    }

    @Override
    public void onSaved() {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.hideProgess();
            subscribedCoursesView.setSaved();
        }
    }
}
