package com.app.studymanager.login;

import android.util.Log;

import com.app.studymanager.models.LoginResponse;

/**
 * Created by Vinay on 23-10-2016.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password) {
        if(loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(email, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onEmailError() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setEmailError();
        }
    }

    @Override
    public void onValidEmailError() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setValidEmailError();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onLoginFailed() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setLoginFailed();;
        }
    }

    @Override
    public void onSuccess(LoginResponse loginResponse) {
        if(loginView != null) {
            loginView.saveUserToken(loginResponse);
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }
}
