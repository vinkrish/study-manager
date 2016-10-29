package com.app.studymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.models.LoginResponse;
import com.app.studymanager.util.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        LoginResponse loginResponse = SharedPreferenceUtil.getUserToken(this);
        if(loginResponse.getAuthToken().equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, BottomBarActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
