package com.app.studymanager.coursesettings;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.DatePickerFragment;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseSettingsActivity extends AppCompatActivity
        implements CourseSettingsView, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private CourseSettingsPresenter presenter;
    private Credentials credentials;
    private Course course;
    private CourseSettings courseSettings;
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            course = (Course)extras.getSerializable("course");
            courseId = course.getId();
        }

        credentials = SharedPreferenceUtil.getUserToken(this);

        presenter = new CourseSettingsPresenterImpl(this, new CourseSettingsInteractorImpl());

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
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
    public void setCourseSettings(CourseSettings courseSettings) {
        this.courseSettings = courseSettings;
        viewPager.setAdapter(new CourseSettingsPagerAdapter(getSupportFragmentManager(),
                course, this.courseSettings));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showSaved() {
        Snackbar.make(coordinatorLayout, getString(R.string.setting_saved), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }

    public void changeDate(View view) {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(courseSettings.getTargetDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        bundle.putInt("day", day);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void saveSettings(View view){
        presenter.onSave(credentials, courseSettings , courseId);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.onResume(credentials, courseId);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date date = cal.getTime();
        courseSettings.setTargetDate(dateFormat.format(date));
        viewPager.setAdapter(new CourseSettingsPagerAdapter(getSupportFragmentManager(),
                course, courseSettings));
        tabLayout.setupWithViewPager(viewPager);
    }
}
