package com.app.studymanager.coursedetails;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;
import com.app.studymanager.rest.CourseApi;
import com.app.studymanager.rest.ErrorUtils;
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

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<Course> courseDetails = api.getCourseDetails(hashMap, courseId);
        courseDetails.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if(response.isSuccessful()) {
                    listener.onFinished(response.body());
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void subscribeToCourse(int userId, String authToken, long courseId,
                                  final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<CommonResponse> subscribeCourse = api.subscribeCourse(hashMap, courseId);
        subscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        listener.onSubscribed();
                    } else {
                        listener.onAPIError("Failed to Subscribe.");
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
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

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

        Call<CommonResponse> unsubscribeCourse = api.unSubscribeCourse(hashMap, courseId);
        unsubscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        listener.onUnSubscribed();
                    } else {
                        listener.onAPIError("Failed to unsubscribe.");
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }


}
