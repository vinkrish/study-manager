package com.app.studymanager.courses;

import com.app.studymanager.models.Course;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.CourseApi;
import com.app.studymanager.rest.ErrorUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 27-10-2016.
 */

public class CoursesInteractorImpl implements CoursesInteractor {
    @Override
    public void fetchCourses(int userId, String authToken, final OnFinishedListener listener) {
        CourseApi api = ApiClient.getClient().create(CourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<List<Course>> subscribedCourses = api.getCourses(hashMap);
        subscribedCourses.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if(response.isSuccessful()) {
                    listener.onFinished(response.body());
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
