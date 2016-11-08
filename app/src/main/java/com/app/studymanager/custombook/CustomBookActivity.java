package com.app.studymanager.custombook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.courseupdate.CourseUpdateActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomBookActivity extends AppCompatActivity implements CustomBookView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.title_et) EditText title;
    @BindView(R.id.pages_et) EditText pages;
    @BindView(R.id.author_et) EditText author;

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

        credentials = SharedPreferenceUtil.getUserToken(this);

        presenter = new CustomBookPresenterImpl(this, new CustomBookInteractorImpl());
    }

    public void createBook(View view) {
        Book book = new Book();
        book.setTitle(title.getText().toString());
        book.setNoOfPages(Integer.parseInt(pages.getText().toString()));
        book.setAuthor(author.getText().toString());
        book.setType("CUSTOM");
        presenter.onAdd(credentials, courseId, book);
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
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Course initCourse = SharedPreferenceUtil.getCourse(this);
        courseId = initCourse.getId();
    }
}
