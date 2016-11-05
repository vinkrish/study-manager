package com.app.studymanager.login;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.R;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.singup.SignupActivity;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.email_et) EditText email;
    @BindView(R.id.password_et) EditText password;
    @BindView(R.id.email) TextInputLayout emailLayout;
    @BindView(R.id.password) TextInputLayout passwordLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        email.addTextChangedListener(new EditTextWatcher(emailLayout));
        password.addTextChangedListener(new EditTextWatcher(passwordLayout));
        presenter = new LoginPresenterImpl(this);
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
    public void setLoginFailed() {
        Snackbar.make(coordinatorLayout, getString(R.string.login_failed), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void navigateToSignup() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    @Override
    public void saveUserToken(Credentials credentials) {
        SharedPreferenceUtil.saveUserToken(this, credentials);
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, BottomBarActivity.class));
        finish();
    }

    @OnClick(R.id.login_btn)
    public void login(View view) {
        if(validate()){
            presenter.validateCredentials(email.getText().toString(), password.getText().toString());
        }
    }

    @OnClick(R.id.signup_btn)
    public void signup(View view){
        navigateToSignup();
    }

    public boolean validate(){
        boolean valid = true;

        if(email.getText().toString().isEmpty()){
            valid = false;
            emailLayout.setError(getString(R.string.email_error));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailLayout.setError(getString(R.string.valid_email));
            valid = false;
        }else if (password.getText().toString().isEmpty() ||
                password.getText().toString().length() < 8) {
            passwordLayout.setError(getString(R.string.valid_password));
            valid = false;
        }

        return valid;
    }

}
