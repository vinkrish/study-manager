package com.app.studymanager.singup;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.util.CommonDialogUtil;
import com.app.studymanager.util.EditTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity implements SignupView {
    @BindView(R.id.email_et) EditText email;
    @BindView(R.id.password_et) EditText password;
    @BindView(R.id.email) TextInputLayout emailLayout;
    @BindView(R.id.password) TextInputLayout passwordLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private SignupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        email.addTextChangedListener(new EditTextWatcher(emailLayout));
        password.addTextChangedListener(new EditTextWatcher(passwordLayout));
        presenter = new SignupPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        emailLayout.setError(getString(R.string.email_error));
    }

    @Override
    public void setValidEmailError() {
        emailLayout.setError(getString(R.string.valid_email));
    }

    @Override
    public void setPasswordError() {
        passwordLayout.setError(getString(R.string.password_error));
    }

    @Override
    public void setValidPasswordError() {
        passwordLayout.setError(getString(R.string.valid_password));
    }

    @Override
    public void setEmailExist() {
        Snackbar.make(coordinatorLayout, getString(R.string.email_exist), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showSignupSuccesful() {
        CommonDialogUtil.displayAlertDialog(this, getString(R.string.verification));
    }

    @Override
    public void showSignupFailed() {
        Snackbar.make(coordinatorLayout, getString(R.string.signup_failed), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.login_btn)
    public void login(View view) {
        navigateToLogin();
    }

    @OnClick(R.id.signup_btn)
    public void signup(View view){
        presenter.validateCredentials(email.getText().toString(), password.getText().toString());
    }
}
