package com.app.studymanager.bottombar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.courses.CoursesFragment;
import com.app.studymanager.customcourse.CustomCourseActivity;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.pwdrecovery.PwdRecoveryActivity;
import com.app.studymanager.subscribedcourses.SubscribedCoursesFragment;
import com.app.studymanager.profile.ProfileFragment;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BottomBarActivity extends AppCompatActivity implements BottomBarView{
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.home) LinearLayout home;
    @BindView(R.id.courses) LinearLayout courses;
    @BindView(R.id.profile) LinearLayout profile;
    @BindView(R.id.home_img) ImageView homeImg;
    @BindView(R.id.courses_img) ImageView coursesImg;
    @BindView(R.id.profile_img) ImageView profileImg;
    @BindView(R.id.home_tv) TextView homeTv;
    @BindView(R.id.courses_tv) TextView coursesTv;
    @BindView(R.id.profile_tv) TextView profileTv;

    private String selectedTab = "home";
    Credentials response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        ButterKnife.bind(this);

        response = SharedPreferenceUtil.getUserToken(this);

        home.setOnClickListener(homeClickListener);
        courses.setOnClickListener(coursesClickListener);
        profile.setOnClickListener(profileClickListener);

        if(selectedTab.equals("home")) {
            navigateToSubscribedCourses();
        } else if (selectedTab.equals("courses")) {
            navigateToCourses();
        } else if(selectedTab.equals("profile")) {
            navigateToProfile();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    View.OnClickListener homeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!selectedTab.equals("home")){
                navigateToSubscribedCourses();
            }
        }
    };

    View.OnClickListener coursesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!selectedTab.equals("courses")){
                navigateToCourses();
            }
        }
    };

    View.OnClickListener profileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!selectedTab.equals("profile")){
                navigateToProfile();
            }
        }
    };

    @Override
    public void navigateToSubscribedCourses() {
        selectedTab = "home";
        clearTabs();
        homeImg.setActivated(true);
        homeTv.setActivated(true);
        Fragment firstFragment = SubscribedCoursesFragment
                .newInstance(response.getUserId(),response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, firstFragment).commit();
    }

    @Override
    public void navigateToCourses() {
        selectedTab = "courses";
        clearTabs();
        coursesImg.setActivated(true);
        coursesTv.setActivated(true);
        Fragment secondFragment = CoursesFragment
                .newInstance(response.getUserId(),response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, secondFragment).commit();
    }

    @Override
    public void navigateToProfile() {
        selectedTab = "profile";
        clearTabs();
        profileImg.setActivated(true);
        profileTv.setActivated(true);
        Fragment thirdFragment = ProfileFragment
                .newInstance(response.getUserId()+"",response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, thirdFragment).commit();
    }

    private void clearTabs(){
        homeImg.setActivated(false);
        coursesImg.setActivated(false);
        profileImg.setActivated(false);
        homeTv.setActivated(false);
        coursesTv.setActivated(false);
        profileTv.setActivated(false);
    }

    public void browseCourses(View view){
        navigateToCourses();
    }

    public void addCourse(View view) {
        startActivity(new Intent(this, CustomCourseActivity.class));
    }

    public void logout(View view){
        SharedPreferenceUtil.logout(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void pwdReset(View view) {
        startActivity(new Intent(this, PwdRecoveryActivity.class));
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void showAPIError(String message) {
        showSnackbar(message);
    }

}
