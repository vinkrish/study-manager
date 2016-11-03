package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 03-11-2016.
 */

public class CourseUpdateInteractorImpl implements CourseUpdateInteractor {
    @Override
    public void fetchCourseDetails(Credentials credentials, long courseId, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("user-id", credentials.getUserId()+"");
        hashMap.put("auth-token", credentials.getAuthToken());

        Call<Course> courseDetails = api.getSubscribedCourseDetails(hashMap, courseId);
        courseDetails.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
