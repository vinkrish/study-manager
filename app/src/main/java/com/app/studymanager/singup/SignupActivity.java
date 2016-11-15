package com.app.studymanager.singup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.util.CommonDialogUtil;
import com.app.studymanager.util.EditTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity implements SignupView {
    @BindView(R.id.email_et) EditText email;
    @BindView(R.id.password_et) EditText password;
    @BindView(R.id.email) TextInputLayout emailLayout;
    @BindView(R.id.password) TextInputLayout passwordLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private SignupPresenter presenter;
    private Snackbar snackbar;

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showSnackbar(String message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
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
    public void setEmailExist() {
        showSnackbar(getString(R.string.email_exist));
    }

    @Override
    public void showSignupSuccessful() {
        CommonDialogUtil.displayAlertDialog(this, getString(R.string.verification));
    }

    @Override
    public void showSignupFailed() {
        showSnackbar(getString(R.string.signup_failed));
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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

    @OnClick(R.id.login_btn)
    public void login(View view) {
        navigateToLogin();
    }

    @OnClick(R.id.signup_btn)
    public void signup(View view){
        if(validate()) {
            presenter.validateCredentials(email.getText().toString(), password.getText().toString());
        }
    }
}
