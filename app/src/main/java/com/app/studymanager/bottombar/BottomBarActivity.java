package com.app.studymanager.bottombar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.studymanager.R;
import com.app.studymanager.courses.CoursesFragment;
import com.app.studymanager.customcourse.CustomCourseActivity;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.pwdrecovery.PwdRecoveryActivity;
import com.app.studymanager.subscribedcourses.SubscribedCoursesFragment;
import com.app.studymanager.profile.ProfileFragment;
import com.app.studymanager.util.SharedPreferenceUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BottomBarActivity extends AppCompatActivity implements BottomBarView {
    Credentials response;
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        response = SharedPreferenceUtil.getUserToken(this);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    navigateToSubscribedCourses();
                } else if (tabId == R.id.tab_courses) {
                    navigateToCourses();
                } else if(tabId == R.id.tab_profile) {
                    navigateToProfile();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void navigateToSubscribedCourses() {
        Fragment firstFragment = SubscribedCoursesFragment
                .newInstance(response.getUserId(),response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, firstFragment).commit();
    }

    @Override
    public void navigateToCourses() {
        Fragment secondFragment = CoursesFragment
                .newInstance(response.getUserId(),response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, secondFragment).commit();
    }

    @Override
    public void navigateToProfile() {
        Fragment thirdFragment = ProfileFragment
                .newInstance(response.getUserId()+"",response.getAuthToken());
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, thirdFragment).commit();
    }

    public void browseCourses(View view){
        bottomBar.selectTabAtPosition(1);
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
}
