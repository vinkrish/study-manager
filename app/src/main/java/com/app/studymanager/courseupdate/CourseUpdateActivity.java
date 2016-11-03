package com.app.studymanager.courseupdate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.DividerItemDecoration;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseUpdateActivity extends AppCompatActivity implements CourseUpdateView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.date_tv) TextView date;
    @BindView(R.id.target_tv) TextView target;
    @BindView(R.id.progress_tv) TextView progress;

    private CourseUpdatePresenter presenter;
    private Credentials credentials;
    private long courseId;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        courseId = getIntent().getLongExtra("courseId", 0);
        endDate = getIntent().getStringExtra("date");

        credentials = SharedPreferenceUtil.getUserToken(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        presenter = new CourseUpdatePresenterImpl(this, new CourseUpdateInteractorImpl());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private String getTargetDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
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
    public void setCourse(Course course) {
        title.setText(course.getTitle());
        date.setText(getTargetDate(endDate));
        target.setText(String.format("Read %s pages today to stay on track", course.getTodayGoal()));
        progress.setText(String.format("Today's progress"));
        recyclerView.setAdapter(new CourseUpdateAdapter(this, course.getBookList()));
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(credentials, courseId);
    }
}
