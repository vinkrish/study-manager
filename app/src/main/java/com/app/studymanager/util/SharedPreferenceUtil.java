package com.app.studymanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.studymanager.models.LoginResponse;

/**
 * Created by Vinay on 26-10-2016.
 */

public class SharedPreferenceUtil {

    public static void saveUserToken(Context context, LoginResponse response) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userId", response.getUserId());
        editor.putString("authToken", response.getAuthToken());
        editor.apply();
    }

    public static LoginResponse getUserToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        LoginResponse response = new LoginResponse();
        response.setUserId(sharedPref.getInt("userId", 0));
        response.setAuthToken(sharedPref.getString("authToken", ""));
        return response;
    }

}
