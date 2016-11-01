package com.app.studymanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 26-10-2016.
 */

public class SharedPreferenceUtil {

    public static void saveUserToken(Context context, Credentials response) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userId", response.getUserId());
        editor.putString("authToken", response.getAuthToken());
        editor.apply();
    }

    public static Credentials getUserToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        Credentials response = new Credentials();
        response.setUserId(sharedPref.getInt("userId", 0));
        response.setAuthToken(sharedPref.getString("authToken", ""));
        return response;
    }

}
