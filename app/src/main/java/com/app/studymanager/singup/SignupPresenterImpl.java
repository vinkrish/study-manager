package com.app.studymanager.singup;

/**
 * Created by Vinay on 23-10-2016.
 */

public class SignupPresenterImpl implements SignupPresenter, SignupInteractor.OnSignupFinishedListener {
    private SignupView signupView;
    private SignupInteractor signupInteractor;

    public SignupPresenterImpl(SignupView signupView){
        this.signupView = signupView;
        this.signupInteractor = new SignupInteractorImpl();
    }

    @Override
    public void onEmailError() {
        if(signupView != null) {
            signupView.hideProgress();
            signupView.setEmailError();
        }
    }

    @Override
    public void onValidEmailError() {
        if(signupView != null) {
            signupView.hideProgress();
            signupView.setValidEmailError();
        }
    }

    @Override
    public void onPasswordError() {
        if(signupView != null) {
            signupView.hideProgress();
            signupView.setPasswordError();
        }
    }

    @Override
    public void onValidPasswordError() {
        if(signupView != null) {
            signupView.hideProgress();
            signupView.setValidPasswordError();
        }
    }

    @Override
    public void onEmailExist() {
        if(signupView != null) {
            signupView.hideProgress();
            signupView.setEmailExist();
        }
    }

    @Override
    public void onSuccess() {
        if(signupView != null){
            signupView.hideProgress();
            signupView.showSignupSuccesful();
        }
    }

    @Override
    public void onFailure() {
        if(signupView != null){
            signupView.hideProgress();
            signupView.showSignupFailed();
        }
    }

    @Override
    public void validateCredentials(String email, String password) {
        if(signupView != null) {
            signupView.showProgress();
        }
        signupInteractor.signup(email, password, this);
    }

    @Override
    public void onDestroy() {
        signupView = null;
    }
}
