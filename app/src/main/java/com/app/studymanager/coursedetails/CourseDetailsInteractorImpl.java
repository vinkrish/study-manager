package com.app.studymanager.coursedetails;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;
import com.app.studymanager.rest.CourseApi;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 28-10-2016.
 */

public class CourseDetailsInteractorImpl implements CourseDetailsInteractor {
    @Override
    public void fetchCourseDetails(int userId, String authToken, long courseId,
                                   final OnFinishedListener listener) {
        CourseApi api = ApiClient.getClient().create(CourseApi.class);

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<Course> courseDetails = api.getCourseDetails(hashMap, courseId);
        courseDetails.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {

            }
        });
    }

    @Override
    public void subscribeToCourse(int userId, String authToken, long courseId,
                                  final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<CommonResponse> subscribeCourse = api.subscribeCourse(hashMap, courseId);
        subscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onSubscribed();
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void unSubscribeToCourse(int userId, String authToken, long courseId,
                                    final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<CommonResponse> unsubscribeCourse = api.unSubscribeCourse(hashMap, courseId);
        unsubscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onUnSubscribed();
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }


}
