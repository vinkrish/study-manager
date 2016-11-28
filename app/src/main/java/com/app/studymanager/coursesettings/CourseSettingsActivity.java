package com.app.studymanager.coursesettings;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studymanager.R;
import com.app.studymanager.coursesettings.fragment.targetdate.TargetDateFragment;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.WeeklyHours;
import com.app.studymanager.util.CommonDialogUtil;
import com.app.studymanager.util.DatePickerFragment;
import com.app.studymanager.util.DateUtil;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private Toast myToast;
    private CourseSettingsPagerAdapter adapter;

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
        setViewPager();
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

    private void showToast(String msg){
        if(myToast != null){
            myToast.cancel();
        }
        myToast = Toast.makeText(this, msg,Toast.LENGTH_LONG);
        myToast.show();
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

    public void saveDifficultySettings(WeeklyHours weeklyHours){
        courseSettings.setWeeklyHours(weeklyHours);
        CourseSettings newCourseSettings = new CourseSettings();
        newCourseSettings.setWeeklyHours(weeklyHours);
        newCourseSettings.setProficiency(courseSettings.getProficiency());
        presenter.onSave(credentials, newCourseSettings , courseId);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date date = cal.getTime();

        Calendar nearestTargetCalendar = Calendar.getInstance();
        Date nearestTargetDate = new Date();
        try {
            nearestTargetDate = dateFormat.parse(courseSettings.getNearestTargetDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nearestTargetCalendar.setTime(nearestTargetDate);

        if(nearestTargetCalendar.get(Calendar.YEAR) > cal.get(Calendar.YEAR)) {
            updateTargetDateSettings(displayFormat.format(date));
            /*showSnackbar(String.format(Locale.ENGLISH,
                    "Cannot complete the course by %s as per your settings",
                    displayFormat.format(date)));*/
            //toggleValidTargetDate(true, dateFormat.format(date));
        } else if(nearestTargetCalendar.get(Calendar.MONTH) > cal.get(Calendar.MONTH) &&
                nearestTargetCalendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            updateTargetDateSettings(displayFormat.format(date));
        } else if(nearestTargetCalendar.get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH) &&
                nearestTargetCalendar.get(Calendar.MONTH) >= cal.get(Calendar.MONTH) &&
                nearestTargetCalendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            updateTargetDateSettings(displayFormat.format(date));
        } else {
            SharedPreferenceUtil.saveTargetDateSettings(this, false, "");
            courseSettings.setTargetDate(dateFormat.format(date));
            setViewPager();
            viewPager.setCurrentItem(0);
            //toggleValidTargetDate(false, "");
        }
    }

    private void updateTargetDateSettings(String date) {
        SharedPreferenceUtil.saveTargetDateSettings(this, true, date);
        setViewPager();
        viewPager.setCurrentItem(0);
    }

    private void toggleValidTargetDate(boolean visible, String date) {
        int pos = viewPager.getCurrentItem();
        Fragment activeFragment = adapter.getItem(pos);
        if(pos == 0)
            ((TargetDateFragment)activeFragment).isValidTargetDate(visible, date);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_easy:
                if (checked){
                    courseSettings.setProficiency("BEGINNER");
                    setViewPager();
                    viewPager.setCurrentItem(1);
                    /*showToast(String.format(Locale.ENGLISH, "Proposed target no of pages per day = %d",
                            courseSettings.getProficiencyValue().getBeginner()));*/
                }
                break;
            case R.id.radio_moderate:
                if (checked){
                    courseSettings.setProficiency("NORMAL");
                    setViewPager();
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.radio_aggressive:
                if (checked){
                    courseSettings.setProficiency("EXPERT");
                    setViewPager();
                    viewPager.setCurrentItem(1);
                }
                break;
        }
    }

    private void setViewPager() {
        adapter = new CourseSettingsPagerAdapter(getSupportFragmentManager(),
                course, courseSettings);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if(courseSettings.getDefaultView()!=null && courseSettings.getDefaultView().equals("TARGET_DATE")){
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }
}
