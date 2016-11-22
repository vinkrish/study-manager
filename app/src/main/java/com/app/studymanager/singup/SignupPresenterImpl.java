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
            signupView.showSignupSuccessful();
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
    public void onAPIError(String message) {
        if(signupView != null){
            signupView.hideProgress();
            signupView.showAPIError(message);
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
