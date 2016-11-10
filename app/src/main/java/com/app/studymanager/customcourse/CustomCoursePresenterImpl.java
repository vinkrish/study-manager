package com.app.studymanager.customcourse;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 10-11-2016.
 */

public class CustomCoursePresenterImpl implements CustomCoursePresenter,
        CustomCourseInteractor.OnFinishedListener {

    private CustomCourseView customCourseView;
    private CustomCourseInteractor interactor;

    public CustomCoursePresenterImpl(CustomCourseView customCourseView, CustomCourseInteractor interactor) {
        this.customCourseView = customCourseView;
        this.interactor = interactor;
    }

    @Override
    public void onAdd(Credentials credentials, Course course) {
        if(customCourseView != null) {
            customCourseView.showProgress();
            interactor.addCustomCourse(credentials, course, this);
        }
    }

    @Override
    public void onDestroy() {
        customCourseView = null;
    }

    @Override
    public void onAdded() {
        if(customCourseView != null) {
            customCourseView.hideProgess();
            customCourseView.setAdd();
        }
    }

    @Override
    public void onError() {
        if(customCourseView != null) {
            customCourseView.hideProgess();
            customCourseView.showError();
        }
    }
}
