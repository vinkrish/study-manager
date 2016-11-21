package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Course;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesInteractorImpl implements SubscribedCoursesInteractor {
    @Override
    public void fetchSubscribedCourses(int userId, String authToken, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<SubscribedCourses> subscribedCourses = api.getSubscribedCourses(hashMap);
        subscribedCourses.enqueue(new Callback<SubscribedCourses>() {
            @Override
            public void onResponse(Call<SubscribedCourses> call, Response<SubscribedCourses> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<SubscribedCourses> call, Throwable t) {

            }
        });
    }
}
