package com.app.studymanager.login;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 23-10-2016.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password) {
        if(loginView != null) {
            loginView.showProgress();
            interactor.login(email, password, this);
        }
    }

    @Override
    public void pwdRecovery(String email) {
        if(loginView != null) {
            loginView.showProgress();
            interactor.recoverPwd(email, this);
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onLoginFailed() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setLoginFailed();;
        }
    }

    @Override
    public void onSuccess(Credentials credentials) {
        if(loginView != null) {
            loginView.saveUserToken(credentials);
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }

    @Override
    public void onPwdRecovered() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.pwdRecovered();
        }
    }

    @Override
    public void onNoUser() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.noUserError();
        }
    }

    @Override
    public void onError() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setError();
        }
    }

    @Override
    public void onAPIError(String message) {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.showAPIError(message);
        }
    }
}
