package com.app.studymanager.pwdrecovery;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 11-11-2016.
 */

public class PwdPresenterImpl implements PwdPresenter, PwdInteractor.OnFinishedListener {
    private PwdView pwdView;
    private PwdInteractor interactor;

    public PwdPresenterImpl(PwdView pwdView, PwdInteractor interactor) {
        this.pwdView = pwdView;
        this.interactor = interactor;
    }

    @Override
    public void onPwdReset() {
        if(pwdView != null) {
            pwdView.hideProgress();
            pwdView.setPwdReset();
        }
    }

    @Override
    public void onError() {
        if(pwdView != null) {
            pwdView.hideProgress();
            pwdView.setError();
        }
    }

    @Override
    public void onAPIError(String message) {
        if(pwdView != null) {
            pwdView.hideProgress();
            pwdView.showAPIError(message);
        }
    }

    @Override
    public void pwdReset(Credentials credentials, String email, String password) {
        if(pwdView != null) {
            pwdView.showProgress();
            interactor.resetPwd(credentials, email, password, this);
        }
    }

    @Override
    public void onDestroy() {
        pwdView = null;
    }
}
