package com.app.studymanager.login;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.R;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.singup.SignupActivity;
import com.app.studymanager.util.CommonDialogUtil;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
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
    public void setError() {
        showSnackbar(getString(R.string.request_error));
    }

    @Override
    public void showAPIError(String message) {
        showSnackbar(message);
    }

    @Override
    public void pwdRecovered() {
        CommonDialogUtil.displayAlertDialog(this, "New password has been sent to your mail");
    }

    @Override
    public void noUserError() {
        showSnackbar("No user found for entered email");
    }

    @Override
    public void navigateToSignup() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    @Override
    public void saveUserToken(Credentials credentials) {
        SharedPreferenceUtil.saveUserToken(this, credentials);
        SharedPreferenceUtil.saveEmail(this, email.getText().toString());
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, BottomBarActivity.class));
        finish();
    }

    public void login(View view) {
        if(validate()){
            presenter.validateCredentials(email.getText().toString(), password.getText().toString());
        }
    }

    public void forgotPassword(View view){
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailLayout.setError(getString(R.string.valid_email));
        } else {
            presenter.pwdRecovery(email.getText().toString());
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
