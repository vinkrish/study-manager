package com.app.studymanager.coursesettings;

import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 31-10-2016.
 */

public class CourseSettingsPresenterImpl implements CourseSettingsPresenter,
        CourseSettingsInteractor.OnFinishedListener {

    private CourseSettingsView settingsView;
    private CourseSettingsInteractor interactor;

    public CourseSettingsPresenterImpl(CourseSettingsView settingsView,
                                       CourseSettingsInteractor interactor){
        this.settingsView = settingsView;
        this.interactor = interactor;
    }

    @Override
    public void onResume(Credentials credentials, long courseId) {
        if(settingsView != null) {
            settingsView.showProgress();
            interactor.fetchCourseSettings(credentials, courseId, this);
        }
    }

    @Override
    public void onSave(Credentials credentials, CourseSettings settings, long courseId) {
        if(settingsView != null) {
            settingsView.showProgress();
            interactor.saveCourseSettings(credentials, settings, courseId, this);
        }
    }

    @Override
    public void onDestroy() {
        settingsView = null;
    }

    @Override
    public void onFinished(CourseSettings courseSettings) {
        if(settingsView != null) {
            settingsView.setCourseSettings(courseSettings);
            settingsView.hideProgess();
        }
    }

    @Override
    public void onSaved() {
        if(settingsView != null){
            settingsView.hideProgess();
            settingsView.showSaved();
        }
    }

    @Override
    public void onError() {
        if(settingsView != null) {
            settingsView.hideProgess();
            settingsView.showError();
        }
    }
}
