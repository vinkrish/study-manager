package com.app.studymanager.coursedetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.coursesettings.CourseSettingsActivity;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.GridSpacingItemDecoration;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseDetailsActivity extends AppCompatActivity implements CourseDetailsView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.check_subscription) Button checkSubscription;
    @BindView(R.id.course_settings) Button courseSettings;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private CourseDetailsPresenter presenter;
    private Credentials response;
    private Course course;
    private long courseId;
    private boolean subscriptionStatus;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = getIntent().getLongExtra("courseId", 0);
            subscriptionStatus = getIntent().getBooleanExtra("subscribed", false);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subscriptionStatus();

        response = SharedPreferenceUtil.getUserToken(this);
        presenter = new CourseDetailsPresenterImpl(this, new CourseDetailsInteractorImpl());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        //layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, false));
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
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setCourse(Course course) {
        this.course = course;
        toolbar.setTitle(course.getTitle());
        description.setText(course.getDescription());
        recyclerView.setAdapter(new CourseDetailsAdapter(this, course.getBookList()));
    }

    private void subscriptionStatus(){
        if(subscriptionStatus){
            checkSubscription.setText(getString(R.string.unsubscribe));
            courseSettings.setVisibility(View.VISIBLE);
        } else {
            checkSubscription.setText(getString(R.string.subscribe));
            courseSettings.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSubscribed() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscribed), Snackbar.LENGTH_LONG)
                .show();
        subscriptionStatus = true;
        subscriptionStatus();
        courseSettings.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnsubscribed() {
        Snackbar.make(coordinatorLayout, getString(R.string.unsubscribed), Snackbar.LENGTH_LONG)
                .show();
        subscriptionStatus = false;
        subscriptionStatus();
        courseSettings.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.error_msg), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showAPIError(String message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void courseSubscription(View view){
        if(subscriptionStatus){
            presenter.unsubscribeCourse(response.getUserId(), response.getAuthToken(), courseId);
        } else {
            presenter.subscribeCourse(response.getUserId(), response.getAuthToken(), courseId);
        }
    }

    public void courseSettings(View view) {
        Intent intent = new Intent(this, CourseSettingsActivity.class);
        Bundle args = new Bundle();
        if(course != null){
            args.putSerializable("course", course);
        }
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(response.getUserId(), response.getAuthToken(), courseId);
    }

}
