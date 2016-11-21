package com.app.studymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.login.LoginActivity;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Credentials credentials = SharedPreferenceUtil.getUserToken(this);
        if(credentials.getAuthToken().equals("")) {
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
