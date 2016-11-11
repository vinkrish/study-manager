package com.app.studymanager.profile;

import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.Profile;

/**
 * Created by Vinay on 11-11-2016.
 */

public class ProfilePresenterImpl implements ProfilePresenter, ProfileInteractor.OnFinishedListener {
    private ProfileView profileView;
    private ProfileInteractorImpl interactor;

    public ProfilePresenterImpl(ProfileView profileView, ProfileInteractorImpl interactor) {
        this.profileView = profileView;
        this.interactor = interactor;
    }


    @Override
    public void onResume(Credentials credentials) {
        if(profileView != null) {
            profileView.showProgress();
            interactor.getProfile(credentials, this);
        }
    }

    @Override
    public void onUpdate(Credentials credentials, String name) {
        if(profileView != null) {
            interactor.updateProfile(credentials, name, this);
        }
    }

    @Override
    public void onDestroy() {
        profileView = null;
    }

    @Override
    public void onFinished(Profile profile) {
        if(profile != null) {
            profileView.hideProgess();
            profileView.setProfile(profile);
        }
    }

    @Override
    public void onSuccess() {
        if(profileView != null) {
            profileView.hideProgess();
            profileView.setSuccess();
        }
    }

    @Override
    public void onError() {
        if(profileView != null) {
            profileView.hideProgess();
            profileView.setError();
        }
    }
}
