package com.app.studymanager.coursesettings;

import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.coursesettings.fragment.difficulty.DifficultyFragment;
import com.app.studymanager.coursesettings.fragment.targetdate.TargetDateFragment;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.WeeklyHours;
import com.app.studymanager.util.CommonDialogUtil;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseSettingsActivity extends AppCompatActivity
        implements CourseSettingsView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_reading_speed:
                loadReadingSpeed();
                return true;
            case R.id.set_target_date:
                loadTargetDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.onResume(credentials, courseId);
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

        if(courseSettings.getDefaultView()!=null && courseSettings.getDefaultView().equals("TARGET_DATE")){
            loadTargetDate();
        } else {
            loadReadingSpeed();
        }
    }

    public void replace(Fragment f, FragmentManager fm) {
        fm
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, f).commit();
    }

    public void loadReadingSpeed() {
        DifficultyFragment fragment = DifficultyFragment.newInstance(course, courseSettings);
        replace(fragment, getSupportFragmentManager());
    }

    public void loadTargetDate() {
        TargetDateFragment fragment = TargetDateFragment.newInstance(course, courseSettings);
        replace(fragment, getSupportFragmentManager());
    }

    @Override
    public void showSaved() {
        Snackbar.make(coordinatorLayout, getString(R.string.setting_saved), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showInputError() {
        CommonDialogUtil.displayAlertDialog(this, getString(R.string.input_hours_error));
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.error_msg), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showAPIError(String message) {
        showSnackbar(message);
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void saveSettings(View view){
        if(courseSettings.getDefaultView()!=null && courseSettings.getDefaultView().equals("TARGET_DATE")) {
            presenter.onSave(credentials, courseSettings , courseId);
        } else {
            courseSettings.setDefaultView("TARGET_DATE");
            showDefault("Course settings has been caliberated based on your Reading-Speed, " +
                    "changing the settings to a target date will clear all of your previous settings.",
                    "DIFFICULTY", courseSettings);
        }
    }

    public void showDefault(String message, final String defaultView, final CourseSettings cs) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Notification");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onSave(credentials, cs, courseId);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                courseSettings.setDefaultView(defaultView);
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    public void saveDifficultySettings(WeeklyHours weeklyHours){
        courseSettings.setWeeklyHours(weeklyHours);
        CourseSettings newCourseSettings = new CourseSettings();
        newCourseSettings.setWeeklyHours(weeklyHours);
        newCourseSettings.setProficiency(courseSettings.getProficiency());

        if(courseSettings.getDefaultView()!=null && courseSettings.getDefaultView().equals("DIFFICULTY")) {
            presenter.onSave(credentials, newCourseSettings , courseId);
        } else {
            courseSettings.setDefaultView("DIFFICULTY");
            showDefault("Course settings has been caliberated based on a Target-Date, " +
                            "Changing the settings to a reading speed will clear all of your previous settings.",
                    "TARGET_DATE", newCourseSettings);
        }
    }

}
