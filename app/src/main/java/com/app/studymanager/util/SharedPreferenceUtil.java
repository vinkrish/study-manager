package com.app.studymanager.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.studymanager.models.Course;
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

    public static void logout(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("authToken", "");
        editor.apply();
    }

    public static void saveEmail(Context context, String email) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public static String getEmail(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user_token", Context.MODE_PRIVATE);
        return sharedPref.getString("email", "");
    }

    public static void saveCourse(Context context, Course course) {
        SharedPreferences sharedPref = context.getSharedPreferences("course", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("id", course.getId());
        editor.apply();
    }

    public static Course getCourse(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("course", Context.MODE_PRIVATE);
        Course response = new Course();
        response.setId(sharedPref.getLong("id", 0));
        return response;
    }

}
