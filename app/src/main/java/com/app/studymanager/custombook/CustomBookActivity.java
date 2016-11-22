package com.app.studymanager.custombook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.courseupdate.CourseUpdateActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomBookActivity extends AppCompatActivity implements CustomBookView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.title_et) EditText title;
    @BindView(R.id.pages_et) EditText pages;
    @BindView(R.id.author_et) EditText author;
    @BindView(R.id.title) TextInputLayout titleInputLayout;
    @BindView(R.id.pages) TextInputLayout pagesInputLayout;

    private CustomBookPresenter presenter;
    private Credentials credentials;
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        title.addTextChangedListener(new EditTextWatcher(titleInputLayout));
        pages.addTextChangedListener(new EditTextWatcher(pagesInputLayout));

        credentials = SharedPreferenceUtil.getUserToken(this);

        presenter = new CustomBookPresenterImpl(this, new CustomBookInteractorImpl());
    }

    public void createBook(View view) {
        if(validate()) {
            Book book = new Book();
            book.setTitle(title.getText().toString());
            book.setNoOfPages(Integer.parseInt(pages.getText().toString()));
            book.setAuthor(author.getText().toString());
            book.setType("CUSTOM");
            presenter.onAdd(credentials, courseId, book);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAdd() {
        startActivity(new Intent(this, CourseUpdateActivity.class));
    }

    @Override
    public void showError() {
        showSnackbar(getString(R.string.error_msg));
    }

    @Override
    public void showAPIError(String message) {
        showSnackbar(message);
    }

    private boolean validate() {
        if(title.getText().toString().isEmpty()){
            titleInputLayout.setError("Title cannot be empty");
           return false;
        } else if(pages.getText().toString().isEmpty()) {
            pagesInputLayout.setError("Pages cannot be empty");
            return false;
        } else return true;
    }

    private void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Course initCourse = SharedPreferenceUtil.getCourse(this);
        courseId = initCourse.getId();
    }
}
