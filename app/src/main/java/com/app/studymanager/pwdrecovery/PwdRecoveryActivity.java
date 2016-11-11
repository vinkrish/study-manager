package com.app.studymanager.pwdrecovery;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PwdRecoveryActivity extends AppCompatActivity implements PwdView{
    @BindView(R.id.mail_et) EditText email;
    @BindView(R.id.mail) TextInputLayout emailLayout;
    @BindView(R.id.pwd_et) EditText password;
    @BindView(R.id.pwd) TextInputLayout passwordLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private PwdPresenter presenter;
    private Credentials credentials;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_recovery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        credentials = SharedPreferenceUtil.getUserToken(this);
        mail = SharedPreferenceUtil.getEmail(this);
        email.setText(mail);

        email.addTextChangedListener(new EditTextWatcher(emailLayout));
        password.addTextChangedListener(new EditTextWatcher(passwordLayout));
        presenter = new PwdPresenterImpl(this, new PwdInteractorImpl());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        Snackbar.make(coordinatorLayout, "Error while requesting, please try again", Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPwdReset() {
        Snackbar.make(coordinatorLayout, "Your password has changed", Snackbar.LENGTH_LONG)
                .show();
    }

    public void resetPassword(View view) {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (password.getText().toString().isEmpty() ||
                password.getText().toString().length() < 8) {
            passwordLayout.setError(getString(R.string.valid_password));
        } else {
            presenter.pwdReset(credentials, mail, password.getText().toString());
        }
    }
}
