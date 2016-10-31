package com.app.studymanager.coursedetails;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.LoginResponse;
import com.app.studymanager.util.GridSpacingItemDecoration;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseDetailsActivity extends AppCompatActivity implements CourseDetailsView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.check_subscription) Button checkSubscription;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private CourseDetailsPresenter presenter;
    private LoginResponse response;
    private long courseId;
    private boolean subscriptionStatus;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseId = getIntent().getLongExtra("courseId", 0);
        subscriptionStatus = getIntent().getBooleanExtra("subscribed", false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

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
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setCourse(Course course) {
        toolbar.setTitle(course.getTitle());
        description.setText(course.getDescription());
        recyclerView.setAdapter(new CourseDetailsAdapter(this, course.getBookList()));
    }

    private void subscriptionStatus(){
        if(subscriptionStatus){
            checkSubscription.setText(getString(R.string.unsubscribe));
        } else {
            checkSubscription.setText(getString(R.string.subscribe));
        }
    }

    @Override
    public void showSubscribed() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscribed), Snackbar.LENGTH_LONG)
                .show();
        subscriptionStatus = true;
        subscriptionStatus();
    }

    @Override
    public void showUnsubscribed() {
        Snackbar.make(coordinatorLayout, getString(R.string.unsubscribed), Snackbar.LENGTH_LONG)
                .show();
        subscriptionStatus = false;
        subscriptionStatus();
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }

    public void courseSubscription(View view){
        if(subscriptionStatus){
            presenter.unsubscribeCourse(response.getUserId(), response.getAuthToken(), courseId);
        } else {
            presenter.subscribeCourse(response.getUserId(), response.getAuthToken(), courseId);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(response.getUserId(), response.getAuthToken(), courseId);
    }
}
